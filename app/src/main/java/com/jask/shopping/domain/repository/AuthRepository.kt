package com.jask.shopping.domain.repository

import com.google.firebase.auth.AuthCredential
import com.google.firebase.auth.AuthResult
import com.jask.shopping.data.model.Product
import com.jask.shopping.util.Resource
import kotlinx.coroutines.flow.Flow

interface AuthRepository{
    fun loginUser(email: String, password:String): Flow<Resource<AuthResult>>
    fun registerUser(email: String, password:String): Flow<Resource<AuthResult>>
    fun googleSignIn(credential: AuthCredential): Flow<Resource<AuthResult>>
    fun sendPasswordResetEmail(email: String): Flow<Resource<Unit>>
    fun googleSignOut()
    fun getSpecialProducts(): Flow<Resource<List<Product>>>
    fun getBestDeals(): Flow<Resource<List<Product>>>
    fun getBestProducts(): Flow<Resource<List<Product>>>
}