package com.jask.shopping.presentation.screens.login_screen

import com.jask.shopping.util.RegisterValidation

data class LoginState(
    val isLoading: Boolean = false,
    val isSuccess: String? = "",
    val isError: String? = "",
    val isSignInSuccessful: Boolean = false,
    val signInError: String? = null,
    val emailRegisterValidation: RegisterValidation = RegisterValidation.RegisterSuccess,
    val passwordRegisterValidation: RegisterValidation = RegisterValidation.RegisterSuccess,
)
