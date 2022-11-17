package com.brandyodhiambo.mynote.feature_auth.data.repository

import com.brandyodhiambo.mynote.feature_auth.domain.model.User
import com.brandyodhiambo.mynote.feature_auth.domain.repository.AuthRepository
import com.brandyodhiambo.mynote.feature_auth.presentation.utils.Resource
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import java.io.IOException
import javax.inject.Inject

class AuthRepostoryImpl @Inject constructor(
    private val firebaseAuth: FirebaseAuth,
    private val firestore: FirebaseFirestore
) :AuthRepository {
    override suspend fun loginUser(email: String, password: String):Resource<AuthResult> {
           return try {
                val authResult = firebaseAuth.signInWithEmailAndPassword(email, password).await()
                Resource.Success(authResult)
            } catch (e: IOException) {
                Resource.Error(e.message.toString())
            } catch (e: Exception) {
                Resource.Error(e.message.toString())
            }

    }

    override suspend fun createUser(email: String, password: String):Resource<AuthResult> {
        return try {
            val result = firebaseAuth.createUserWithEmailAndPassword(email, password).await()
            val firebaseUser = firebaseAuth.currentUser
            val uid = result.user?.uid
            val user = User(userId = uid!!, email = email, password = password)
            firebaseUser?.sendEmailVerification()
            firestore.collection("users").document(uid).set(user).await()
            Resource.Success(result)
        } catch (e:IOException){
            Resource.Error(message = "Check your internet connection", data = null)
        } catch (e:Exception){
            Resource.Error(message = "Unknown error occurred", data = null)
        }
    }

    override fun userAutologin(): Boolean {
        return firebaseAuth.currentUser != null
    }

    override fun signOut() {
        firebaseAuth.signOut()
    }

    override suspend fun forgotPassword(email: String):Resource<Any>{
        return withContext(Dispatchers.IO){
            firebaseAuth.sendPasswordResetEmail(email).await()
            Resource.Success(Any())
        }
    }
}