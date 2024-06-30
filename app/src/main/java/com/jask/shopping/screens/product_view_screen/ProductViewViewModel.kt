package com.jask.shopping.screens.product_view_screen

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jask.shopping.data.model.CartProduct
import com.jask.shopping.repository.AuthRepository
import com.jask.shopping.util.Resource
import com.jask.shopping.util.validateEmail
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProductViewViewModel @Inject constructor(private  val repository: AuthRepository)  :ViewModel() {

    private val _state = mutableStateOf(ProductViewStates())
    val state: State<ProductViewStates> = _state

    private fun addUpdateProduct(cartProduct: CartProduct) = viewModelScope.launch {

        repository.addProductToCart(cartProduct = cartProduct).collect { result ->
            when (result) {
                is Resource.Loading -> {
                    _state.value = _state.value.copy(isLoading = true)
                }

                is Resource.Success -> {
                    _state.value = _state.value.copy(isLoading = false)
                    Log.d("TAG", "product added")
                }

                is Resource.Error -> {
                    Log.d("TAG", "getAllItems: error")
                }
            }
        }
    }

    private fun getAllItems(category: String) = viewModelScope.launch {

        repository.getAllProducts(category = category).collect { result ->
            when (result) {
                is Resource.Loading -> {
                    _state.value = _state.value.copy(isLoading = true)
                }

                is Resource.Success -> {
                    _state.value = _state.value.copy(allProducts = result.data!!)
                    _state.value = _state.value.copy(isLoading = false)
                }

                is Resource.Error -> {
                    Log.d("TAG", "getAllItems: error")
                }
            }
        }
    }

    fun onEvent(event: ProductViewEvents) {
        when (event){
            is ProductViewEvents.GetProductsByCategory -> {
                getAllItems(event.category)
            }
            is ProductViewEvents.AddUpdateProduct -> {
                addUpdateProduct(event.cartProduct)
            }
        }
    }
}

