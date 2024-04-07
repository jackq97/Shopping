package com.jask.shopping.domain.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import com.google.firebase.auth.AuthCredential
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.jask.shopping.data.model.Product
import com.jask.shopping.data.model.paging.ProductsPagingSource
import com.jask.shopping.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val firebaseAuth: FirebaseAuth,
    private val firestore: FirebaseFirestore,
    private val config: PagingConfig
) : AuthRepository  {

    override fun loginUser(email: String, password: String): Flow<Resource<AuthResult>> {return flow {
            emit(Resource.Loading())
            val result = firebaseAuth.signInWithEmailAndPassword(
                email, password).await()
            emit(Resource.Success(result))
        }.catch {
            emit(Resource.Error(it.message.toString()))
        }
    }

    override fun registerUser(email: String, password: String): Flow<Resource<AuthResult>> {
        return flow {
            emit(Resource.Loading())
            val result = firebaseAuth.createUserWithEmailAndPassword(
                email, password).await()
            emit(Resource.Success(result))
        }.catch {
            emit(Resource.Error(it.message.toString())) }
    }

    override fun googleSignIn(credential: AuthCredential): Flow<Resource<AuthResult>> {
        return flow {
            emit(Resource.Loading())
            val result = firebaseAuth.signInWithCredential(credential).await()
            emit(Resource.Success(result))
        }.catch {
            emit(Resource.Error(it.message.toString()))
        }
    }

    override fun sendPasswordResetEmail(email: String): Flow<Resource<Unit>> = flow {
        emit(Resource.Loading())
        try {
            firebaseAuth.sendPasswordResetEmail(email).await()
            emit(Resource.Success(Unit))
        } catch (e: Exception) {
            emit(Resource.Error(e.message ?: "An error occurred"))
        }
    }

    override fun googleSignOut() {
        firebaseAuth.signOut()
    }

    override fun getPaginatedSpecialItemProducts() = Pager(
        config = config
    ) {
        ProductsPagingSource(queryProductsByName = firestore.collection("Products")
            .whereEqualTo("category", "special item"))
    }.flow

    override fun getPaginatedBestDealsProducts() = Pager(
        config = config
    ) {
        ProductsPagingSource(queryProductsByName = firestore.collection("Products")
            .whereEqualTo("category", "best deals"))
    }.flow

    override fun getPaginatedBestProducts() = Pager(
        config = config
    ) {
        ProductsPagingSource(queryProductsByName = firestore.collection("Products")
            .whereEqualTo("category", "best products"))
    }.flow

    override fun getAllProducts(category: String): Flow<Resource<List<Product>>> = flow {
        emit(Resource.Loading())
        try {
            val result = firestore.collection("Products")
                .whereEqualTo("category", category)
                .get()
                .await()
            val productList = result.toObjects(Product::class.java)
            emit(Resource.Success(productList))
        } catch (e: Exception) {
            emit(Resource.Error(e.message ?: "Unknown error occurred"))
        }
    }


    /*override fun getBestDeals(): Flow<Resource<List<Product>>> = flow {
        emit(Resource.Loading())
        try {
            val result = firestore.collection("Products")
                .whereEqualTo("category", "best deals")
                .get()
                .await()

            val specialProductList = result.toObjects(Product::class.java)
            emit(Resource.Success(specialProductList))
        } catch (e: Exception) {
            emit(Resource.Error(e.message ?: "Unknown error occurred"))
        }
    }

    override fun getBestProducts(): Flow<Resource<List<Product>>> = flow {
        emit(Resource.Loading())
        try {
            val result = firestore.collection("Products")
                .whereEqualTo("category", "best products")
                .get()
                .await()

            val specialProductList = result.toObjects(Product::class.java)
            emit(Resource.Success(specialProductList))
        } catch (e: Exception) {
            emit(Resource.Error(e.message ?: "Unknown error occurred"))
        }
    }*/
}