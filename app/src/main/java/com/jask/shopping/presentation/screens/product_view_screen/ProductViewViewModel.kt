package com.jask.shopping.presentation.screens.product_view_screen

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jask.shopping.domain.repository.AuthRepository
import com.jask.shopping.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProductViewViewModel @Inject constructor(private  val repository: AuthRepository)  :ViewModel() {

    private val _state = mutableStateOf(ProductViewStates())
    val state: MutableState<ProductViewStates> = _state

    init {
        getSpecialItem()
    }

    private fun getSpecialItem() = viewModelScope.launch {

            repository.getSpecialProducts().collect { result ->
                when (result) {
                    is Resource.Loading -> {
                        Log.d("TAG", "getSpecialItem: loading") }
                    is Resource.Success -> {
                        _state.value = _state.value.copy(specialProduct = result.data!!)
                        Log.d("TAG", "getSpecialItem: success")
                    }
                    is Resource.Error -> {
                        Log.d("TAG", "getSpecialItem: error") }
                    }
                }
            }
}
