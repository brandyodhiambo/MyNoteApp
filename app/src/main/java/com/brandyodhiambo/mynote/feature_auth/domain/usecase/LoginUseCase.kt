package com.brandyodhiambo.mynote.feature_auth.domain.usecase

import com.brandyodhiambo.mynote.feature_auth.domain.repository.AuthRepository
import com.brandyodhiambo.mynote.feature_auth.presentation.utils.Resource
import com.google.firebase.auth.AuthResult
import javax.inject.Inject

class LoginUseCase (
    private val authRepository: AuthRepository
) {
    suspend  fun loginUser(email:String,password:String):Resource<AuthResult>{
        return authRepository.loginUser(email, password)
    }
}