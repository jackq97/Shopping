package com.jask.shopping.domain.repository

import androidx.paging.PagingData
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
    fun getAllProducts(category: String): Flow<Resource<List<Product>>>
    //fun getBestDeals(): Flow<Resource<List<Product>>>
    //fun getBestProducts(): Flow<Resource<List<Product>>>
    fun getPaginatedBestProducts(): Flow<PagingData<Product>>
    fun getPaginatedBestDealsProducts(): Flow<PagingData<Product>>
    fun getPaginatedSpecialItemProducts(): Flow<PagingData<Product>>
}