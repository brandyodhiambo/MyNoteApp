package com.brandyodhiambo.mynote.feature_auth.presentation.screens.login

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.brandyodhiambo.mynote.feature_auth.domain.usecase.ForgotPasswordCase
import com.brandyodhiambo.mynote.feature_auth.domain.usecase.LoginUseCase
import com.brandyodhiambo.mynote.feature_auth.domain.usecase.SignUpUseCase
import com.brandyodhiambo.mynote.feature_auth.domain.usecase.validation.ValidateEmail
import com.brandyodhiambo.mynote.feature_auth.domain.usecase.validation.ValidatePassword
import com.brandyodhiambo.mynote.feature_auth.presentation.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val loginUseCase: LoginUseCase,
    private val validateEmail: ValidateEmail,
    private val validatePassword: ValidatePassword
):ViewModel() {
    var loginState by mutableStateOf(LoginScreenState())

    private val _loginValidationChannel = Channel<LoginScreenVaidationEvents>()
    val loginValidationChannel = _loginValidationChannel.receiveAsFlow()

    fun onEvent(event:LoginScreenEvents){
        when(event){
            is LoginScreenEvents.EmailFieldChangeEvent ->{
                loginState = loginState.copy(email = event.email)
            }
            is LoginScreenEvents.PasswordFieldChangeEvent ->{
                loginState = loginState.copy(password = event.password)
            }
            is LoginScreenEvents.SubmitButtonClickEvent ->{
                submitData()
            }
        }
    }

    private fun submitData() {
        val emailResult = validateEmail.execute(loginState.email)
        val passwordResult = validatePassword.execute(loginState.password)

        val hasError = listOf(
            emailResult,
            passwordResult
        ).any {
            !it.isSuccessful
        }
        loginState = loginState.copy(
            emailError = emailResult.errorMessage,
            passwordError = passwordResult.errorMessage,
        )
        if(hasError){
            return
        }

        viewModelScope.launch {
            _loginValidationChannel.send(LoginScreenVaidationEvents.Success)
        }
    }

    fun loginUser(){
        loginState = loginState.copy(showProgressBar = true)
        viewModelScope.launch {
            val result = loginUseCase.loginUser(loginState.email, loginState.password)
            when(result){
             is Resource.Success ->{
                 loginState = loginState.copy(showProgressBar = false)
                 _loginValidationChannel.send(LoginScreenVaidationEvents.ValidationSuccess)
             }
             is Resource.Error ->{
                 loginState = loginState.copy(showProgressBar = false)
                 val errorMessage = result.message?: "An unexpected error occurred"
                    _loginValidationChannel.send(LoginScreenVaidationEvents.ValidationError(errorMessage))
             }
                else -> Unit
         }
        }
    }

    sealed class LoginScreenVaidationEvents{
        object ValidationSuccess : LoginScreenVaidationEvents()
        object Success : LoginScreenVaidationEvents()
        data class ValidationError (val errorMessage:String?): LoginScreenVaidationEvents()
    }
}

