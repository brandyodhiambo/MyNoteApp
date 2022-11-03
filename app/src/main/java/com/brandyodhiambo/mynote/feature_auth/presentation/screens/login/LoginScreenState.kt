package com.brandyodhiambo.mynote.feature_auth.presentation.screens.login

data class LoginScreenState(
    val email : String = "" ,
    val emailError : String? = null,
    val password : String = "",
    val passwordError : String? = null,
    val showProgressBar:Boolean = false
)