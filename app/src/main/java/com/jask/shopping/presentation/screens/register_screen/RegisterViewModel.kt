package com.jask.shopping.presentation.screens.register_screen

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jask.shopping.domain.repository.AuthRepository
import com.jask.shopping.util.RegisterValidation
import com.jask.shopping.util.Resource
import com.jask.shopping.util.validateEmail
import com.jask.shopping.util.validatePassword
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class RegisterViewModel @Inject constructor (
    private val repository: AuthRepository
): ViewModel() {

    private val _state = mutableStateOf(RegisterStates())
    val state: State<RegisterStates> = _state

    private fun registerUser(email: String, password: String) = viewModelScope.launch {

        repository.registerUser(email = email, password = password).collect { result ->
            when (result) {

                is Resource.Loading -> {
                    _state.value = _state.value.copy(isLoading = true)
                }

                is Resource.Success -> {
                    _state.value = _state.value.copy(isSuccess = "Sign Up Success")
                    _state.value = _state.value.copy(isLoading = false)
                    Log.d("viewModel", "registerUser: success")
                }

                is Resource.Error -> {
                    _state.value = _state.value.copy(isError = result.message)
                }
            }
        }
    }

    fun onEvent(event: RegisterEvents) {
        when (event){
            is RegisterEvents.CreateAccountWithEmailAndPassword -> {
                if (checkValidation(event.email, event.password)) {
                    registerUser(email = event.email, password = event.password)
                } else {
                    _state.value = _state.value.copy( emailRegisterValidation = validateEmail(email = event.email) )
                    _state.value = _state.value.copy( passwordRegisterValidation = validatePassword(password = event.password) )
                }
            }
        }
    }

    private fun checkValidation(email: String, password: String): Boolean {
        val emailValidation = validateEmail(email)
        val passwordValidation = validatePassword(password)
        return emailValidation is RegisterValidation.Success &&
                passwordValidation is RegisterValidation.Success
    }
}