package com.brandyodhiambo.mynote.feature_auth.presentation.screens.forgotpassword

data class ForgotPasswordState(
    val email : String = "" ,
    val emailError : String? = null,
    val showProgressBar:Boolean = false
)