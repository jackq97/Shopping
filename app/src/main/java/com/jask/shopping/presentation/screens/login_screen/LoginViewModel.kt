package com.jask.shopping.presentation.screens.login_screen

import androidx.lifecycle.ViewModel
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.State
import com.jask.shopping.domain.repository.AuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(private val repository: AuthRepository)  : ViewModel () {

    private val _state = mutableStateOf(LoginState())
    val state: State<LoginState> = _state

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

    suspend fun sendResetEmail(email: String){
        repository.sendPasswordResetEmail(email)
    }

}