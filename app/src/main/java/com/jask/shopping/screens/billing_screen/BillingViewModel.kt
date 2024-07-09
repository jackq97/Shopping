package com.jask.shopping.screens.billing_screen

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jask.shopping.data.model.Order
import com.jask.shopping.repository.AuthRepository
import com.jask.shopping.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BillingViewModel @Inject constructor(private val repository: AuthRepository): ViewModel() {

    private val _state = mutableStateOf(BillingStates())
    val state: State<BillingStates> = _state

    init {
        getCartProduct()
        getAddress()
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

    private fun getAddress() = viewModelScope.launch {
        repository.getAddresses().collect { result ->
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
                        addressList = result.data!!
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

    private fun placeOrder(order: Order) = viewModelScope.launch {
        repository.placeOrder(order).collect { result ->
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
                }
                is Resource.Error -> {
                    _state.value = _state.value.copy(
                        isError = result.message
                    )
                }
            }
        }
    }

    fun onEvent(events: BillingEvents){

        when (events){
            is BillingEvents.PlaceOrder -> {
                placeOrder(events.order)
            }
        }
    }

}