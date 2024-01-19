package com.jask.shopping.presentation.screens.register_screen

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jask.shopping.domain.repository.AuthRepository
import com.jask.shopping.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class RegisterViewmodel @Inject constructor (
    private val repository: AuthRepository
): ViewModel() {

    private val _state = mutableStateOf(RegisterStates())
    val state: State<RegisterStates> = _state

    private fun registerUser(email: String, password: String) = viewModelScope.launch {

        repository.registerUser(email = email, password = password).collect{
            result ->
            when (result) {

                is Resource.Loading -> {
                    _state.value = _state.value.copy( isLoading = true )
                }

                is Resource.Success -> {
                    _state.value = _state.value.copy( isSuccess = "Sign Up Success" )
                    Log.d("viewModel", "registerUser: success")
                }

                is Resource.Error -> {
                    _state.value = _state.value.copy( isError = result.message )
                }
            }
        }
    }

    fun onEvent(event: RegisterEvents) {
        when (event){
            is RegisterEvents.CreateAccountWithEmailAndPassword -> {
                registerUser(email = event.email, password = event.password)
            }
        }

    }
}
