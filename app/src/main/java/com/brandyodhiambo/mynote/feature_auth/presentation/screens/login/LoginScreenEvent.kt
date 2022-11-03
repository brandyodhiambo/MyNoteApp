package com.brandyodhiambo.mynote.feature_auth.presentation.screens.login

sealed class LoginScreenEvents {

    data class EmailFieldChangeEvent(val email : String) : LoginScreenEvents()
    data class PasswordFieldChangeEvent(val password : String) : LoginScreenEvents()

    object SubmitButtonClickEvent : LoginScreenEvents()

}