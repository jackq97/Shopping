package com.jask.shopping.repository

import android.util.Log
import androidx.paging.Pager
import androidx.paging.PagingConfig
import com.google.firebase.auth.AuthCredential
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.jask.shopping.data.model.Address
import com.jask.shopping.data.model.CartProduct
import com.jask.shopping.data.model.Order
import com.jask.shopping.data.model.Product
import com.jask.shopping.data.paging.ProductsPagingSource
import com.jask.shopping.screens.login_screen.SignInResult
import com.jask.shopping.util.Constants.PAGE_SIZE
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
) : AuthRepository {

    /* override suspend fun signOut(): Flow<Response<Boolean>> = flow {
        try {
            emit(Loading)
            auth.currentUser?.apply {
                delete().await()
                emit(Success(true))
            }
        } catch (e: Exception) {
            emit(Error(e.message ?: ERROR_MESSAGE))
        }
    }*/

    override fun loginUser(email: String, password: String): Flow<Resource<AuthResult>> {return flow {
            emit(Resource.Loading())
            val result = firebaseAuth.signInWithEmailAndPassword(
                email, password).await()
            emit(Resource.Success(result))
        }.catch {
            emit(Resource.Error(it.message.toString()))
        }
    }

    override fun registerUser(email: String,
                              password: String,
                              firstName: String,
                              lastName: String
    ): Flow<Resource<AuthResult>> {
        return flow {
            emit(Resource.Loading())
            val result = firebaseAuth.createUserWithEmailAndPassword(
                email, password).await()
            emit(Resource.Success(result))
            val uid = result.user!!.uid
            val userMap = hashMapOf(
                "uid" to uid,
                "firstName" to firstName,
                "lastName" to lastName
            )
            firestore.collection("users").document(uid).set(userMap)
                .addOnSuccessListener {
                    Log.d("TAG", "User information added to Firestore successfully")
                }
                .addOnFailureListener { e ->
                    Log.w("TAG", "Error adding user information to Firestore", e
                )
            }
        }.catch {
            emit(Resource.Error(it.message.toString()))
        }
    }

    override fun uploadUserDataWithGoogleSignIn(signInResult: SignInResult): Flow<Resource<Unit>> = flow {
        emit(Resource.Loading())
        try {
            val result = signInResult.data
            val uid = result?.userId ?: throw Exception("User not authenticated")
            val name = result.username
            val nameParts = name?.split(" ")
            val firstName = nameParts?.firstOrNull() ?: ""
            val lastName = nameParts?.lastOrNull() ?: ""

            val userMap = hashMapOf(
                "uid" to uid,
                "firstName" to firstName,
                "lastName" to lastName
            )
            // Use await() to handle the Firestore operation
            firestore.collection("users").document(uid).set(userMap).await()
            Log.d("TAG", "User information added to Firestore successfully")
            emit(Resource.Success(Unit))
        } catch (e: Exception) {
            Log.w("TAG", "Error adding user information to Firestore", e)
            emit(Resource.Error(e.message ?: "Unknown error occurred"))
        }
    }

    override fun addAddress(address: Address): Flow<Resource<Unit>> = flow{
        emit(Resource.Loading())
        val uid = firebaseAuth.currentUser?.uid ?: throw Exception("User not authenticated")
        try {
        val userMap = hashMapOf(
            "addressTitle" to address.addressTitle,
            "fullName" to address.fullName,
            "street" to address.street,
            "phone" to address.phone,
            "city" to address.city,
            "state" to address.street
        )
        val result = firestore.collection("users").document(uid).collection(
            "address"
        ).document()
            .set(userMap).addOnSuccessListener {
                Log.d("TAG", "addAddress: address data added")
            }.addOnFailureListener{
                Log.d("TAG", "addAddress: error adding address data")
            }
        } catch (e: Exception) {
            emit(Resource.Success(Unit))
        }
    }

    override fun increaseProductQuantity(cartProductId: String): Flow<Resource<Unit>> = flow {
        emit(Resource.Loading())
        val uid = firebaseAuth.currentUser?.uid ?: throw Exception("User not authenticated")
        val cartCollection = firestore.collection("users").document(uid).collection("cart")
        try {
            val cartProduct = firestore.collection("users").document(uid).collection("cart")
                .whereEqualTo("product.id", cartProductId).get().await()

            firestore.runTransaction { transaction ->
                val documentRef = cartCollection.document(cartProduct.first().id)
                val document = transaction.get(documentRef)
                val productObject = document.toObject(CartProduct::class.java)
                productObject?.let {
                    val newQuantity = it.quantity + 1
                    val newProductObject = it.copy(quantity = newQuantity)
                    transaction.set(documentRef, newProductObject)
                }
            }.await() // Ensure to await the transaction
            Log.d("TAG", "increaseProductQuantity: quantity increased")
            emit(Resource.Success(Unit))
        } catch (e: Exception) {
            Log.d("TAG", "increaseProductQuantity: $e quantity increase Failed error")
            emit(Resource.Error(e.message ?: "Unknown error occurred"))
        }
    }

    override fun decreaseProductQuantity(cartProductId: String): Flow<Resource<Unit>> =  flow{
        emit(Resource.Loading())
        val uid = firebaseAuth.currentUser?.uid ?: throw Exception("User not authenticated")
        val cartCollection = firestore.collection("users").document(uid).collection("cart")
        try {
            val cartProduct = firestore.collection("users").document(uid).collection("cart")
                .whereEqualTo("product.id", cartProductId).get().await()

            firestore.runTransaction { transaction ->
                val documentRef = cartCollection.document(cartProduct.first().id)
                val document = transaction.get(documentRef)
                val productObject = document.toObject(CartProduct::class.java)
                if(productObject?.quantity != 1){
                    productObject?.let {
                        val newQuantity = it.quantity - 1
                        val newProductObject = it.copy(quantity = newQuantity)
                        transaction.set(documentRef, newProductObject)
                    }
                }
            }.await() // Ensure to await the transaction
            Log.d("TAG", "increaseProductQuantity: quantity increased")
            emit(Resource.Success(Unit))
        } catch (e: Exception) {
            Log.d("TAG", "increaseProductQuantity: $e quantity increase Failed error")
            emit(Resource.Error(e.message ?: "Unknown error occurred"))
        }
    }

    override fun addProductToCart(cartProduct: CartProduct): Flow<Resource<Unit>> = flow {

        emit(Resource.Loading())

        val uid = firebaseAuth.currentUser?.uid ?: throw Exception("User not authenticated")
        val cartCollection = firestore.collection("users").document(uid).collection("cart")

        try {
            fun increaseQuantity(documentId: String) = firestore.runTransaction { transaction ->
                val documentRef = cartCollection.document(documentId)
                val document = transaction.get(documentRef)
                val productObject = document.toObject(CartProduct::class.java)
                productObject?.let {
                    val newQuantity = it.quantity + 1
                    val newProductObject = it.copy(quantity = newQuantity)
                    transaction.set(documentRef, newProductObject)
                }
            }

            val querySnapshot = cartCollection.whereEqualTo("product.id", cartProduct.product.id).get().await()
            if (querySnapshot.isEmpty) {
                // Add new product
                cartCollection.document().set(cartProduct).await()
                Log.d("TAG", "addProductToCart: product added")
                emit(Resource.Success(Unit))
            } else {
                val documentSnapshot = querySnapshot.documents.first()
                val product = documentSnapshot.toObject(CartProduct::class.java)
                if (product?.product?.id == cartProduct.product.id) {
                    // Increase the quantity
                    increaseQuantity(documentSnapshot.id)
                    Log.d("TAG", "addProductToCart: quantity increased")
                    emit(Resource.Success(Unit))
                } else {
                    // Add new product
                    cartCollection.document().set(cartProduct).await()
                    Log.d("TAG", "addProductToCart: product added")
                    emit(Resource.Success(Unit))
                }
            }
        } catch (e: Exception) {
            Log.d("TAG", "addProductToCart: error $e")
            emit(Resource.Error(e.message))
        }
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
        Log.d("TAG", "googleSignOut: signed out")
    }

    override fun getProductById(id: String): Flow<Resource<Product>> = flow {
        emit(Resource.Loading())
        try {
            val result = firestore.collection("Products")
                .whereEqualTo("id", id)
                .get()
                .await()
            val product = result.first().toObject(Product::class.java)
            emit(Resource.Success(product))
        } catch (e: Exception) {
            emit(Resource.Error(e.message ?: "Unknown error occurred"))
        }
    }

    override fun getOrderById(orderId: String): Flow<Resource<Order>> = flow{
        emit(Resource.Loading())
        try {
            val uid =  firebaseAuth.currentUser?.uid ?: throw Exception("User not authenticated")
            Log.d("TAG", "getOrderById: repository $uid")
            val result = firestore
                .collection("users")
                .document(uid)
                .collection("orders")
                .whereEqualTo("orderId", orderId.toLong())
                .get()
                .await()
            Log.d("Firestore", "Number of documents retrieved: ${result.size()}")
            val order = result.first().toObject(Order::class.java)
            emit(Resource.Success(order))
        } catch (e: Exception) {
            emit(Resource.Error(e.message ?: "Unknown error occurred"))
        }
    }

    override fun placeOrder(order: Order): Flow<Resource<Unit>> = flow{
        emit(Resource.Loading())
        val uid = firebaseAuth.currentUser?.uid ?: throw Exception("User not authenticated")
        try {
            val userMap = hashMapOf(
                "orderStatus" to order.orderStatus,
                "totalPrice" to order.totalPrice,
                "products" to order.products,
                "address" to order.address,
                "date" to order.date,
                "orderId" to order.orderId
            )
           firestore.collection("users").document(uid).collection(
                "orders"
            ).document()
                .set(userMap).addOnSuccessListener {
                    Log.d("TAG", "order: orders data added")
                }.addOnFailureListener{
                    Log.d("TAG", "order: error adding order data")
                }
        } catch (e: Exception) {
            emit(Resource.Success(Unit))
        }
    }

    override fun getAllOrders(): Flow<Resource<List<Order>>> = flow{
        emit(Resource.Loading())
        try {
            val uid =  firebaseAuth.currentUser?.uid ?: throw Exception("User not authenticated")
            val result = firestore.collection("users").document(uid).collection("orders")
                .get().await()
            val orderList = result.toObjects(Order::class.java)
            emit(Resource.Success(orderList))
        } catch (e: Exception){
            emit(Resource.Error(e.message ?: "Unknown error occurred"))
        }
    }

    override fun getCartProducts(): Flow<Resource<List<CartProduct>>> = flow {
        emit(Resource.Loading())
        try {
            val uid =  firebaseAuth.currentUser?.uid ?: throw Exception("User not authenticated")
            val result = firestore.collection("users").document(uid).collection("cart")
                .get().await()
            val cartProductList = result.toObjects(CartProduct::class.java)
            emit(Resource.Success(cartProductList))
        } catch (e: Exception){
            emit(Resource.Error(e.message ?: "Unknown error occurred"))
        }
    }

    override fun deleteCartCollection(): Flow<Resource<Unit>> = flow {
        try {
            val uid = firebaseAuth.currentUser?.uid ?: throw Exception("User not authenticated")
            val cartCollection = firestore.collection("users").document(uid).collection("cart")

            // Retrieve all documents in the cart collection
            val documents = cartCollection.get().await()
            // Delete each document
            for (document in documents) {
                cartCollection.document(document.id).delete().await()
            }

            emit(Resource.Success(Unit))
        } catch (e: Exception) {
            emit(Resource.Error(e.message ?: "Unknown error occurred"))
        }
    }


    override fun getAddresses(): Flow<Resource<List<Address>>> = flow {
        emit(Resource.Loading())
        try {
            val uid =  firebaseAuth.currentUser?.uid ?: throw Exception("User not authenticated")
            val result = firestore.collection("users").document(uid).collection("address")
                .get().await()
            val addressList = result.toObjects(Address::class.java)
            emit(Resource.Success(addressList))
        } catch (e: Exception){
            emit(Resource.Error(e.message ?: "Unknown error occurred"))
        }
    }

    // paginated products
    override fun getPaginatedBestProducts(category: String) = Pager(config = config) {

        var query = firestore.collection("Products")
            .whereEqualTo("dealType", "best products")
            .limit(PAGE_SIZE
        )

        if (category.isNotEmpty()) {
            query = firestore.collection("Products")
                .whereEqualTo("dealType", "best products")
                .whereEqualTo("category", category)
                .limit(PAGE_SIZE
            )
        }

        ProductsPagingSource(queryProductsByName = query)

    }.flow

    override fun getPaginatedBestDealsProducts(category: String) = Pager(config = config) {

        // Start with the base query
        var query = firestore.collection("Products")
            .whereEqualTo("dealType", "best deals")
            .limit(PAGE_SIZE
        )

        // Conditionally add the category filter if category is not null or empty
        if (category.isNotEmpty()) {
            query = firestore.collection("Products")
                .whereEqualTo("dealType", "best deals")
                .whereEqualTo("category", category)
                .limit(PAGE_SIZE)
        }

        ProductsPagingSource(queryProductsByName = query)

    }.flow

    override fun getPaginatedSpecialItemProducts(category: String) = Pager(config = config) {

        // Start with the base query
        var query = firestore.collection("Products")
            .whereEqualTo("dealType", "special item")
            .limit(PAGE_SIZE
        )

        if (category.isNotEmpty()) {
            query = firestore.collection("Products")
                .whereEqualTo("dealType", "special item")
                .whereEqualTo("category", category)
                .limit(PAGE_SIZE)
        }

        ProductsPagingSource(queryProductsByName = query)
    }.flow

}