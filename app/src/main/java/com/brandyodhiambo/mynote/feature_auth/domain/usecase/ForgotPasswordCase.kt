package com.brandyodhiambo.mynote.feature_auth.domain.usecase

import com.brandyodhiambo.mynote.feature_auth.domain.repository.AuthRepository
import com.brandyodhiambo.mynote.feature_auth.presentation.utils.Resource
import javax.inject.Inject

class ForgotPasswordCase @Inject constructor (
    private val authRepository: AuthRepository
) {
    suspend  fun forgotPassword(email:String):Resource<Any>{
        return authRepository.forgotPassword(email)
    }
}