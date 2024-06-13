package com.jask.shopping.screens.home_feed

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.jask.shopping.repository.AuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
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
    }

    fun onEvent(event: HomeFeedEvents) {
        when (event){
            is HomeFeedEvents.GetSpecialProductData -> {

            }
        }
    }
}