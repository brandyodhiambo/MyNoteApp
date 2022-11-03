package com.brandyodhiambo.mynote.feature_auth.presentation.screens.signup

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.brandyodhiambo.mynote.feature_auth.domain.usecase.SignUpUseCase
import com.brandyodhiambo.mynote.feature_auth.domain.usecase.validation.ValidateEmail
import com.brandyodhiambo.mynote.feature_auth.domain.usecase.validation.ValidatePassword
import com.brandyodhiambo.mynote.feature_auth.presentation.screens.login.LoginScreenVaidationEvents
import com.brandyodhiambo.mynote.feature_auth.presentation.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class SignUpViewModel @Inject constructor(
    private val signUpUseCase: SignUpUseCase,
    private val validateEmail: ValidateEmail,
    private val validatePassword: ValidatePassword
): ViewModel() {
    var signUpState by mutableStateOf(SignUpScreenState())

    private val _signUpValidationChannel = Channel<SignUpValidationEvent>()
    val signUpValidationChannel = _signUpValidationChannel.receiveAsFlow()


    fun onEvent(event: SignUpScreenEvent){
        when(event){
            is SignUpScreenEvent.EmailFieldChangeEvent->{
                signUpState = signUpState.copy(
                    email = event.email
                )
            }
            is SignUpScreenEvent.PasswordFieldChangeEvent->{
                signUpState = signUpState.copy(
                    password = event.password
                )
            }
            is SignUpScreenEvent.SubmitButtonClickEvent->{
                submitData()
            }
        }
    }

    private fun submitData() {
        val emailResult = validateEmail.execute(signUpState.email)
        val passwordResult = validatePassword.execute(signUpState.password)

        val hasError = listOf(
            emailResult,
            passwordResult
        ).any {
            !it.isSuccessful
        }
        signUpState = signUpState.copy(
            emailError = emailResult.errorMessage,
            passwordError = passwordResult.errorMessage
        )
        if(!hasError){
            Timber.d("onEvent Error: $hasError")
        }

        viewModelScope.launch {
            _signUpValidationChannel.send(SignUpValidationEvent.Success)
        }
    }

    fun signUpUser(){
        signUpState = signUpState.copy(showProgressBar = true)
        viewModelScope.launch {
            when(signUpUseCase.signUpUser(signUpState.email, signUpState.password)){
                is Resource.Success ->{
                    signUpState = signUpState.copy(showProgressBar = false)
                    _signUpValidationChannel.send(SignUpValidationEvent.ValidationSuccess)
                }
                is Resource.Error ->{
                    signUpState = signUpState.copy(showProgressBar = false)
                    val errorMessage = "An unexpected error occurred"
                    _signUpValidationChannel.send(SignUpValidationEvent.ValidationError(errorMessage))
                }
                else -> Unit
            }
        }
    }


}

sealed class SignUpValidationEvent{
    object ValidationSuccess:SignUpValidationEvent()
    object Success:SignUpValidationEvent()
    data class ValidationError(val errorMessage:String?):SignUpValidationEvent()
}