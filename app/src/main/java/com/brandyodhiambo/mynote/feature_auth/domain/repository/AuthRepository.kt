package com.brandyodhiambo.mynote.feature_auth.domain.repository

interface AuthRepository {
    suspend fun loginUser(email: String, password: String)

    suspend fun createUser(email: String, password: String)
}