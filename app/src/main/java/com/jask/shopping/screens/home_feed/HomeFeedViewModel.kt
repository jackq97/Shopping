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
        //fetchProducts()
        _state.value = _state.value.copy(
            specialProduct = repository.getPaginatedSpecialItemProducts().cachedIn(viewModelScope),
            bestProducts = repository.getPaginatedBestProducts().cachedIn(viewModelScope),
            bestDeals = repository.getPaginatedBestDealsProducts().cachedIn(viewModelScope)
        )
        getCartProduct()
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
                    Log.d("TAG", "view model retrieving product")
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
                    Log.d("TAG", "view model Product: state updated ${result.data.size}")
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
        }
    }
}