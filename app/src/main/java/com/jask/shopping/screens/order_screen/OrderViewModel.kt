package com.jask.shopping.screens.order_screen

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
class OrderViewModel @Inject constructor(private val repository: AuthRepository): ViewModel() {

    private val _state = mutableStateOf(OrderStates())
    val state: State<OrderStates> = _state

    init {
        getAllOrders()
    }

    private fun getAllOrders() = viewModelScope.launch {
        repository.getAllOrders().collect { result ->
            when (result) {
                is Resource.Loading -> {
                    _state.value = _state.value.copy(
                        isLoading = true
                    )
                    Log.d("TAG", "loading")
                }
                is Resource.Success -> {
                    _state.value = _state.value.copy(
                        isLoading = false,
                        isSuccess = true,
                        order = result.data!!
                    )
                    Log.d("TAG", "getAllOrders: $result")
                }
                is Resource.Error -> {
                    _state.value = _state.value.copy(
                        isError = result.message
                    )
                    Log.d("TAG", "error ${result.message}")
                }
            }
        }
    }
}