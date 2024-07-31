package com.jask.shopping.screens.home_feed

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.jask.shopping.data.model.CartProduct
import com.jask.shopping.repository.AuthRepository
import com.jask.shopping.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeFeedViewModel @Inject constructor(
    private val repository: AuthRepository
) : ViewModel() {

    private var _state = mutableStateOf(HomeFeedStates())
    val state: State<HomeFeedStates> = _state

    init {
        fetchProducts(category = "")
        getCartProduct()
        Log.d("TAG", "init block : intialized")
    }

    private fun fetchProducts(category: String) = viewModelScope.launch {
        _state.value = _state.value.copy(
            specialProduct = repository.getPaginatedSpecialItemProducts(category).cachedIn(viewModelScope),
            bestProducts = repository.getPaginatedBestProducts(category).cachedIn(viewModelScope),
            bestDeals = repository.getPaginatedBestDealsProducts(category).cachedIn(viewModelScope)
        )
    }

    private fun addUpdateProduct(cartProduct: CartProduct) = viewModelScope.launch {
        repository.addProductToCart(cartProduct = cartProduct).collect { result ->
            when (result) {
                is Resource.Loading -> {
                    _state.value = _state.value.copy(isLoading = true)
                }
                is Resource.Success -> {
                    _state.value = _state.value.copy(
                        isLoading = false,
                        isSuccess = true
                    )
                    getCartProduct()
                }
                is Resource.Error -> {
                    _state.value = _state.value.copy(isError = result.message!!)
                }
            }
        }
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
                        cartProducts = result.data!!
                    )
                }
                is Resource.Error -> {
                    _state.value = _state.value.copy(
                        isError = result.message!!
                    )
                }
            }
        }
    }

    fun onEvent(event: HomeFeedEvents) {
        when (event){
            is HomeFeedEvents.GetCartProductData -> {
                getCartProduct()
            }
            is HomeFeedEvents.AddUpdateProduct -> {
                addUpdateProduct(event.cartProduct)
            }
            is HomeFeedEvents.GetDataByCategory -> {
                fetchProducts(event.category)
            }
        }
    }
}