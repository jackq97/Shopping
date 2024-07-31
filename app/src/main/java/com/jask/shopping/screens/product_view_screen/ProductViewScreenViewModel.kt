package com.jask.shopping.screens.product_view_screen

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jask.shopping.data.model.CartProduct
import com.jask.shopping.repository.AuthRepository
import com.jask.shopping.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProductViewScreenViewModel @Inject constructor(private  val repository: AuthRepository)  :ViewModel() {

    private val _state = mutableStateOf(ProductViewStates())
    val state: State<ProductViewStates> = _state

    private fun addUpdateProduct(cartProduct: CartProduct) = viewModelScope.launch {
        repository.addProductToCart(cartProduct = cartProduct).collect { result ->
            when (result) {
                is Resource.Loading -> {
                    _state.value = _state.value.copy(isAddProductLoading = true)
                }
                is Resource.Success -> {
                    _state.value = _state.value.copy(isAddProductLoading = false)
                    _state.value = _state.value.copy(isAddProductSuccess = true)
                }
                is Resource.Error -> {
                    Log.d("TAG", " viewModel addUpdateProduct: error ${result.message}")
                }
            }
        }
    }

    private fun getProduct(id: String) = viewModelScope.launch {
        repository.getProductById(id = id).collect { result ->
            when (result) {
                is Resource.Loading -> {
                    _state.value = _state.value.copy(isLoading = true)
                }
                is Resource.Success -> {
                    _state.value = _state.value.copy(isLoading = false)
                    _state.value = _state.value.copy(product = result.data!!)
                    _state.value = _state.value.copy(isSuccess = true)
                }
                is Resource.Error -> {
                    Log.d("TAG", "get Product: ${result.message}")
                }
            }
        }
    }

    fun onEvent(event: ProductViewEvents) {
        when (event){
            is ProductViewEvents.GetProductsByCategory -> {
                getProduct(event.id)
            }
            is ProductViewEvents.AddUpdateProduct -> {
                addUpdateProduct(event.cartProduct)
            }
        }
    }
}