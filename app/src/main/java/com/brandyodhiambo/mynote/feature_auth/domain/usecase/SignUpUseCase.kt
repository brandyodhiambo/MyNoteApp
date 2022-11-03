package com.brandyodhiambo.mynote.feature_auth.domain.usecase

import com.brandyodhiambo.mynote.feature_auth.domain.repository.AuthRepository
import com.brandyodhiambo.mynote.feature_auth.presentation.utils.Resource
import com.google.firebase.auth.AuthResult

class SignUpUseCase(
    private val authRepository: AuthRepository
) {
    suspend  fun signUpUser(email:String,password:String): Resource<AuthResult> {
        return authRepository.createUser(email, password)
    }
}