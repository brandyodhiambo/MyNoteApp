package com.brandyodhiambo.mynote.feature_auth.domain.usecase

import com.brandyodhiambo.mynote.feature_auth.domain.repository.AuthRepository

class AutoSignOutUseCase (
    private val authRepository: AuthRepository
) {
    fun autoSignOut(){
        authRepository.signOut()
    }
}