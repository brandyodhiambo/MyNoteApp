package com.brandyodhiambo.mynote.feature_auth.presentation.screens.forgotpassword

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.brandyodhiambo.mynote.feature_auth.domain.usecase.ForgotPasswordCase
import com.brandyodhiambo.mynote.feature_auth.domain.usecase.validation.ValidateEmail
import com.brandyodhiambo.mynote.feature_auth.presentation.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ForgotPasswordViewModel @Inject constructor(
    private val forgotPasswordCase: ForgotPasswordCase,
    private val validateEmail: ValidateEmail,
): ViewModel() {
    var forgotState by mutableStateOf(ForgotPasswordState())

    private val _forgotValidationChannel = Channel<ForgotScreenVaidationEvents>()
    val forgotValidationChannel = _forgotValidationChannel.receiveAsFlow()

    fun onEvent(event: ForgotPasswordScreenEvents){
        when(event){
            is ForgotPasswordScreenEvents.EmailFieldChangeEvent ->{
                forgotState = forgotState.copy(email = event.email)
            }
            is ForgotPasswordScreenEvents.SubmitButtonClickEvent ->{
                submitData()
            }
        }
    }

    private fun submitData() {
        val emailResult = validateEmail.execute(forgotState.email)

        val hasError = listOf(
            emailResult,
        ).any {
            !it.isSuccessful
        }
        forgotState = forgotState.copy(
            emailError = emailResult.errorMessage,
        )
        if(hasError){
            return
        }

        viewModelScope.launch {
            _forgotValidationChannel.send(ForgotScreenVaidationEvents.Success)
        }
    }

    fun forgotPassword(){
        forgotState = forgotState.copy(showProgressBar = true)
        viewModelScope.launch {
            when(forgotPasswordCase.forgotPassword(forgotState.email)){
                is Resource.Success ->{
                    forgotState = forgotState.copy(showProgressBar = false)
                    _forgotValidationChannel.send(ForgotScreenVaidationEvents.ValidationSuccess)
                }
                is Resource.Error ->{
                    forgotState = forgotState.copy(showProgressBar = false)
                    val errorMessage = "An unexpected error occurred"
                    _forgotValidationChannel.send(ForgotScreenVaidationEvents.ValidationError(errorMessage))
                }
                else -> Unit
            }
        }
    }

    sealed class ForgotScreenVaidationEvents{
        object ValidationSuccess : ForgotScreenVaidationEvents()
        object Success : ForgotScreenVaidationEvents()
        data class ValidationError (val errorMessage:String?): ForgotScreenVaidationEvents()
    }
}

