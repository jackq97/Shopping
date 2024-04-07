package com.jask.shopping.presentation.screens.product_view_screen

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jask.shopping.domain.repository.AuthRepository
import com.jask.shopping.presentation.screens.login_screen.LoginEvents
import com.jask.shopping.util.Resource
import com.jask.shopping.util.validateEmail
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProductViewViewModel @Inject constructor(private  val repository: AuthRepository)  :ViewModel() {

    private val _state = mutableStateOf(ProductViewStates())
    val state: State<ProductViewStates> = _state

    fun onEvent(event: ProductViewEvents) {
        when (event){
            is ProductViewEvents.GetProductsByCategory -> {
                getAllItems(event.category)
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
}

