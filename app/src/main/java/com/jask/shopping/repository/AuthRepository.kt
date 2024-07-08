package com.jask.shopping.repository

import androidx.paging.PagingData
import com.google.firebase.auth.AuthCredential
import com.google.firebase.auth.AuthResult
import com.jask.shopping.data.model.Address
import com.jask.shopping.data.model.CartProduct
import com.jask.shopping.data.model.Order
import com.jask.shopping.data.model.Product
import com.jask.shopping.screens.login_screen.SignInResult
import com.jask.shopping.util.Resource
import kotlinx.coroutines.flow.Flow

interface AuthRepository{
    fun loginUser(email: String, password:String): Flow<Resource<AuthResult>>
    fun registerUser(email: String,
                     password: String,
                     firstName: String,
                     lastName: String): Flow<Resource<AuthResult>>
    fun uploadUserDataWithGoogleSignIn(signInResult: SignInResult): Flow<Resource<Unit>>
    fun addAddress(address: Address): Flow<Resource<Unit>>
    fun increaseProductQuantity(cartProductId: String): Flow<Resource<Unit>>
    fun decreaseProductQuantity(cartProductId: String): Flow<Resource<Unit>>
    fun getCartProducts(): Flow<Resource<List<CartProduct>>>
    fun getAddresses(): Flow<Resource<List<Address>>>
    fun addProductToCart(cartProduct: CartProduct) : Flow<Resource<Unit>>
    fun googleSignIn(credential: AuthCredential): Flow<Resource<AuthResult>>
    fun sendPasswordResetEmail(email: String): Flow<Resource<Unit>>
    fun googleSignOut()
    fun getProductById(id: String): Flow<Resource<Product>>
    fun placeOrder(order: Order) : Flow<Resource<Unit>>
    fun getAllOrders(): Flow<Resource<List<Order>>>
    fun getPaginatedBestProducts(): Flow<PagingData<Product>>
    fun getPaginatedBestDealsProducts(): Flow<PagingData<Product>>
    fun getPaginatedSpecialItemProducts(): Flow<PagingData<Product>>
}