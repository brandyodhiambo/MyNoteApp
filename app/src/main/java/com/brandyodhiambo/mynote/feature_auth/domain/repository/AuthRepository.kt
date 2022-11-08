package com.brandyodhiambo.mynote.feature_auth.domain.repository

import com.brandyodhiambo.mynote.feature_auth.presentation.utils.Resource
import com.google.firebase.auth.AuthResult

interface AuthRepository {
    suspend fun loginUser(email: String, password: String):Resource<AuthResult>

    suspend fun createUser(email: String, password: String):Resource<AuthResult>

    fun userAutologin(): Boolean

    fun signOut()

    suspend fun forgotPassword(email: String):Resource<Any>
}