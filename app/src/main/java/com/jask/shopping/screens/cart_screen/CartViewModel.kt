package com.jask.shopping.screens.cart_screen

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jask.shopping.repository.AuthRepository
import com.jask.shopping.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CartViewModel @Inject constructor(private val repository: AuthRepository) : ViewModel() {

    private val _state = mutableStateOf(CartStates())
    val state: State<CartStates> = _state

    init {
        getCartProduct()
    }

    private fun getCartProduct() = viewModelScope.launch {
        repository.getCartProducts().collect { result ->
            when (result) {
                is Resource.Loading -> {
                    _state.value = _state.value.copy(
                        isLoading = true
                    )
                }
                is Resource.Success -> {
                    _state.value = _state.value.copy(
                        isLoading = false,
                        isSuccess = true,
                        cartProduct = result.data!!
                    )
                }
                is Resource.Error -> {
                    _state.value = _state.value.copy(
                        isError = result.message
                    )
                }
            }
        }
    }

    private fun increaseProductQuantity(documentId: String) = viewModelScope.launch {
        repository.increaseProductQuantity(documentId).collect { result ->
            when (result) {
                is Resource.Loading -> {
                    _state.value = _state.value.copy(
                        isLoading = true
                    )
                }
                is Resource.Success -> {
                    _state.value = _state.value.copy(
                        isLoading = false,
                        isSuccess = true,
                    )
                    getCartProduct()
                }
                is Resource.Error -> {
                    _state.value = _state.value.copy(
                        isError = result.message
                    )
                }
            }
        }
    }

    private fun decreaseProductQuantity(documentId: String) = viewModelScope.launch {
        repository.decreaseProductQuantity(documentId).collect { result ->
            when (result) {
                is Resource.Loading -> {
                    _state.value = _state.value.copy(
                        isLoading = true
                    )
                }
                is Resource.Success -> {
                    _state.value = _state.value.copy(
                        isLoading = false,
                        isSuccess = true,
                    )
                    getCartProduct()
                }
                is Resource.Error -> {
                    _state.value = _state.value.copy(
                        isError = result.message
                    )
                }
            }
        }
    }

    fun onEvent(event: CartEvents) {
        when (event) {
            is CartEvents.IncreaseQuantity -> {
                increaseProductQuantity(event.documentId)
            }
            is CartEvents.DecreaseQuantity -> {
                decreaseProductQuantity(event.documentId)
            }
        }
    }
}