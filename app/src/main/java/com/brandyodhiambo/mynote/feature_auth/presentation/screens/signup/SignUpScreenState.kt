package com.brandyodhiambo.mynote.feature_auth.presentation.screens.signup

data class SignUpScreenState(
    val email : String = "" ,
    val emailError : String? = null,
    val password : String = "",
    val passwordError : String? = null,
    val showProgressBar:Boolean = false
)