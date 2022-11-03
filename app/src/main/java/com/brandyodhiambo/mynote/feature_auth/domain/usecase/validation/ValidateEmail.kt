package com.brandyodhiambo.mynote.feature_auth.domain.usecase.validation

import android.util.Patterns


/*
* this is a use-case for validating email field*/

class ValidateEmail {
    fun execute(email:String):ValidationResults{
        if(email.isBlank()){
            return ValidationResults(
                isSuccessful = false,
                errorMessage = "Email is Required"
            )
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            return ValidationResults(
                isSuccessful = false,
                errorMessage = "Not a valid email address"
            )
        }
        return ValidationResults(
            isSuccessful = true
        )
    }
}