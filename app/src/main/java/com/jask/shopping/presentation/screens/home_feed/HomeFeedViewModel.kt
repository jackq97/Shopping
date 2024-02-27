package com.jask.shopping.presentation.screens.home_feed

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.toObjects
import com.jask.shopping.data.model.Product
import com.jask.shopping.presentation.screens.register_screen.RegisterStates
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import com.jask.shopping.util.Resource
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeFeedViewModel @Inject constructor(
    private val firestore: FirebaseFirestore
) : ViewModel() {

    private val _specialProducts = MutableStateFlow<Resource<List<Product>>>(Resource.Loading())
    val specialProducts: StateFlow<Resource<List<Product>>> = _specialProducts

    private var _state = mutableStateOf(HomeFeedStates())
    val state: State<HomeFeedStates> = _state

    init {
        fetchSpecialProducts()

        viewModelScope.launch {
            _specialProducts.collect{product ->
                when(product){
                    is Resource.Loading  -> {

                    }
                    is Resource.Success  -> {
                        _state = _state.value.copy(product = product.data)
                    }
                    is Resource.Error  -> {

                    }
                }
            }
        }
    }

    private fun fetchSpecialProducts(){
        viewModelScope.launch {
            _specialProducts.emit(Resource.Loading())
        }
        firestore.collection("Products")
            .whereEqualTo("category", "Special Products").get().addOnSuccessListener {result->
                val specialProductList = result.toObjects(Product::class.java)
                viewModelScope.launch {

                    _specialProducts.emit(Resource.Success(specialProductList))
                }
            }.addOnFailureListener {
                viewModelScope.launch {
                    _specialProducts.emit(Resource.Error(it.message.toString()))
                }
            }
    }
}