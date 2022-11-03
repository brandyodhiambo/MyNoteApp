package com.brandyodhiambo.mynote.feature_auth.domain.usecase.validation

import com.brandyodhiambo.mynote.feature_auth.domain.utils.AuthConstants.PASSWORD_LENGTH

class ValidatePassword {
    fun execute(password:String):ValidationResults{
        if(password.length<PASSWORD_LENGTH ){
            return ValidationResults(
                isSuccessful = false,
                errorMessage = "Password Is too short"
            )
        }
        val containLetterAndDigits= password.any { it.isDigit() } && password.any { it.isLetter() }
        if(!containLetterAndDigits){
            return ValidationResults(
                isSuccessful = false,
                errorMessage = "Password should contain at least one digit and a letter"
            )
        }
        return ValidationResults(
            isSuccessful = true,
        )
    }
}