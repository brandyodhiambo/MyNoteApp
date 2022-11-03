package com.brandyodhiambo.mynote.feature_auth.data.repository

import com.brandyodhiambo.mynote.feature_auth.domain.repository.AuthRepository
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class AuthRepostoryImpl @Inject constructor(
    private val firebaseAuth: FirebaseAuth
) :AuthRepository {
    override suspend fun loginUser(email: String, password: String) {
        firebaseAuth.signInWithEmailAndPassword(email, password).await()
    }

    override suspend fun createUser(email: String, password: String) {
        firebaseAuth.createUserWithEmailAndPassword(email, password).await()
    }
}