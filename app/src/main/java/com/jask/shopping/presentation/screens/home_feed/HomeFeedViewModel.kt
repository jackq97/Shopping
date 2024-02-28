package com.jask.shopping.presentation.screens.home_feed

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.toObjects
import com.jask.shopping.data.model.Product
import com.jask.shopping.domain.repository.AuthRepository
import com.jask.shopping.presentation.screens.login_screen.LoginEvents
import com.jask.shopping.presentation.screens.register_screen.RegisterStates
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import com.jask.shopping.util.Resource
import com.jask.shopping.util.validateEmail
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeFeedViewModel @Inject constructor(
    private val repository: AuthRepository
) : ViewModel() {

    private var _state = mutableStateOf(HomeFeedStates())
    val state: State<HomeFeedStates> = _state

    private fun getSpecialItem() = viewModelScope.launch {

        repository.getSpecialProducts().collect { result ->
            when (result) {
                is Resource.Loading -> {
                    //_state.value = _state.value.copy(isLoading = true)
                }
                is Resource.Success -> {
                    _state.value = _state.value.copy(specialProduct = result.data)
                    //_state.value = _state.value.copy(isLoading = false)
                    //state.value = _state.value.copy(isSuccess = true)
                    Log.d("viewModel", "data get success")
                }
                is Resource.Error -> {
                    //_state.value = _state.value.copy(isError = result.message)
                }
            }
        }
    }

    fun onEvent(event: HomeFeedEvents) {
        when (event){
            is HomeFeedEvents.GetSpecialProductData -> {
                getSpecialItem()
            }
        }
    }
}