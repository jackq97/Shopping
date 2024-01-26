package com.jask.shopping.domain.repository

import com.google.firebase.auth.AuthCredential
import com.google.firebase.auth.AuthResult
import com.jask.shopping.util.Resource
import kotlinx.coroutines.flow.Flow

interface AuthRepository{
    fun loginUser(email: String, password:String): Flow<Resource<AuthResult>>
    fun registerUser(email: String, password:String): Flow<Resource<AuthResult>>
    fun googleSignIn(credential: AuthCredential): Flow<Resource<AuthResult>>
    suspend fun sendPasswordResetEmail(email: String): Flow<Resource<AuthResult>>
    fun googleSignOut()
}