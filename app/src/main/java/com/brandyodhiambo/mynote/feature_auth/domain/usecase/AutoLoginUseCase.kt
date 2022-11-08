package com.brandyodhiambo.mynote.feature_auth.domain.usecase

import com.brandyodhiambo.mynote.feature_auth.domain.repository.AuthRepository

class AutoLoginUseCase (
    private val authRepository: AuthRepository
) {
    fun autoLogin():Boolean{
        return authRepository.userAutologin()
    }
}