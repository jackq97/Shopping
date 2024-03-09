package com.jask.shopping.presentation.screens.home_feed

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.jask.shopping.domain.repository.AuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import com.jask.shopping.util.Resource
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeFeedViewModel @Inject constructor(
    private val repository: AuthRepository
) : ViewModel() {

    private var _state = mutableStateOf(HomeFeedStates())
    val state: State<HomeFeedStates> = _state

    init {
        _state.value = _state.value.copy(specialProduct = repository.getPaginatedSpecialProducts().cachedIn(viewModelScope))
        _state.value = _state.value.copy(bestProducts = repository.getPaginatedBestProducts().cachedIn(viewModelScope))
        _state.value = _state.value.copy(bestDeals = repository.getPaginatedBestDeals().cachedIn(viewModelScope))
        /*getSpecialItem()
        getBestDealItem()
        getBestProductItem()*/
    }

    /*private fun getSpecialItem() = viewModelScope.launch {

        repository.getSpecialProducts().collect { result ->
            when (result) {
                is Resource.Loading -> {
                    //_state.value = _state.value.copy(isLoading = true)
                }
                is Resource.Success -> {
                    _state.value = _state.value.copy(specialProduct = result.data)

                    //_state.value = _state.value.copy(isLoading = false)
                    //state.value = _state.value.copy(isSuccess = true)
                    Log.d("viewModel", "data get success ${result.data?.first()?.category} ")
                }
                is Resource.Error -> {
                    //_state.value = _state.value.copy(isError = result.message)
                    Log.d("viewModel", "data get failed")
                }
            }
        }
    }*/

    /*private fun getBestDealItem() = viewModelScope.launch {

        repository.getBestDeals().collect { result ->
            when (result) {
                is Resource.Loading -> {
                    //_state.value = _state.value.copy(isLoading = true)
                }
                is Resource.Success -> {
                    _state.value = _state.value.copy(bestDeals = result.data)

                    //_state.value = _state.value.copy(isLoading = false)
                    //state.value = _state.value.copy(isSuccess = true)
                    Log.d("viewModel", "data get success ${result.data?.first()?.category} ")
                }
                is Resource.Error -> {
                    //_state.value = _state.value.copy(isError = result.message)
                    Log.d("viewModel", "data get failed")
                }
            }
        }
    }

    private fun getBestProductItem() = viewModelScope.launch {

        repository.getBestProducts().collect { result ->
            when (result) {
                is Resource.Loading -> {
                    //_state.value = _state.value.copy(isLoading = true)
                }
                is Resource.Success -> {
                    _state.value = _state.value.copy(bestProducts = result.data)

                    //_state.value = _state.value.copy(isLoading = false)
                    //state.value = _state.value.copy(isSuccess = true)
                    Log.d("viewModel", "data get success ${result.data?.first()?.category} ")
                }
                is Resource.Error -> {
                    //_state.value = _state.value.copy(isError = result.message)
                    Log.d("viewModel", "data get failed")
                }
            }
        }
    }*/

    fun onEvent(event: HomeFeedEvents) {
        when (event){
            is HomeFeedEvents.GetSpecialProductData -> {

            }
        }
    }
}