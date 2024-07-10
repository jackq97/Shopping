package com.jask.shopping.screens.order_details_screen

import android.util.Log
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
class OrderDetailsViewModel @Inject constructor(private val repository: AuthRepository): ViewModel() {

    private val _state = mutableStateOf(OrderDetailsStates())
    val state: State<OrderDetailsStates> = _state


    private fun getOrder(id: String) = viewModelScope.launch {

        repository.getOrderById(orderId = id).collect { result ->
            when (result) {
                is Resource.Loading -> {
                    _state.value = _state.value.copy(isLoading = true)
                }
                is Resource.Success -> {
                    _state.value = _state.value.copy(isLoading = false)
                    _state.value = _state.value.copy(order = result.data!!)
                }
                is Resource.Error -> {
                    Log.d("TAG", "get order: ${result.message}")
                }
            }
        }
    }

    fun onEvent(event: OrderEvents) {
        when (event){
            is OrderEvents.GetOrderById -> {
                getOrder(id = event.id)
            }
        }
    }
}