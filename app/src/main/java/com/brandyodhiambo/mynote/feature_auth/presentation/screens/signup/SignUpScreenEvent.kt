package com.brandyodhiambo.mynote.feature_auth.presentation.screens.signup

sealed class SignUpScreenEvent {

    data class EmailFieldChangeEvent(val email : String) : SignUpScreenEvent()
    data class PasswordFieldChangeEvent(val password : String) : SignUpScreenEvent()

    object SubmitButtonClickEvent : SignUpScreenEvent()

}