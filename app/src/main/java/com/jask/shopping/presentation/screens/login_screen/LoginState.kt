package com.jask.shopping.presentation.screens.login_screen

data class LoginState(
    val isSignInSuccessful: Boolean = false,
    val signInError: String? = null
)
