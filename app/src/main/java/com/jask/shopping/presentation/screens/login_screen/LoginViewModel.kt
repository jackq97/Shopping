package com.jask.shopping.presentation.screens.login_screen

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.State
import androidx.lifecycle.viewModelScope
import com.jask.shopping.domain.repository.AuthRepository
import com.jask.shopping.presentation.screens.register_screen.RegisterEvents
import com.jask.shopping.util.RegisterValidation
import com.jask.shopping.util.Resource
import com.jask.shopping.util.validateEmail
import com.jask.shopping.util.validatePassword
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(private val repository: AuthRepository)  : ViewModel () {

    private val _state = mutableStateOf(LoginState())
    val state: State<LoginState> = _state

    private fun sendResetEmail(email: String) = viewModelScope.launch {

        repository.sendPasswordResetEmail(email).collect { result ->
            when (result) {
                is Resource.Loading -> {
                    _state.value = _state.value.copy(isLoading = true)
                }
                is Resource.Success -> {
                    _state.value = _state.value.copy(isLoading = false)
                    _state.value = _state.value.copy(isSuccess = "Reset email sent successfully")
                    Log.d("viewModel", "Reset email: Success")
                }
                is Resource.Error -> {
                    _state.value = _state.value.copy(isError = result.message)
                }
            }
        }
    }

    fun onEvent(event: LoginEvents) {
        when (event){
            is LoginEvents.SendPasswordResetEmail -> {
                if (checkValidation(event.email)) {
                    sendResetEmail(email = event.email)
                } else {
                    _state.value = _state.value.copy( emailRegisterValidation = validateEmail(email = event.email) )
                }
            }
        }
    }

    fun onSignInResult(result: SignInResult) {
        _state.value = _state.value.copy(
            isSignInSuccessful = result.data != null,
            signInError = result.errorMessage
        )
    }

    fun resetState() {
        _state.value = _state.value.copy(   isSignInSuccessful = false,
        signInError = null )
    }

    private fun checkValidation(email: String): Boolean {
        val emailValidation = validateEmail(email)
        return emailValidation is RegisterValidation.RegisterSuccess}

}