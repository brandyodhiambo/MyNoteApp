package com.brandyodhiambo.mynote.feature_auth.domain.usecase

import com.brandyodhiambo.mynote.feature_auth.domain.repository.AuthRepository
import javax.inject.Inject

class LoginUseCase (
    private val authRepository: AuthRepository
) {
    suspend operator fun invoke(email:String,password:String){
        authRepository.loginUser(email, password)
    }
}