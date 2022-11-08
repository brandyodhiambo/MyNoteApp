package com.brandyodhiambo.mynote.feature_auth.presentation.screens.forgotpassword

sealed class ForgotPasswordScreenEvents {

    data class EmailFieldChangeEvent(val email : String) : ForgotPasswordScreenEvents()

    object SubmitButtonClickEvent : ForgotPasswordScreenEvents()

}