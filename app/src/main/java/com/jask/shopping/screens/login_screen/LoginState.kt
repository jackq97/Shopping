package com.jask.shopping.screens.login_screen

import com.jask.shopping.util.RegisterValidation

data class LoginState(
    val isLoading: Boolean = false,
    val isSuccess: Boolean = false,
    val isError: String? = "",
    val isSignInSuccessful: Boolean = false,
    val signInError: String? = null,
    val emailRegisterValidation: RegisterValidation = RegisterValidation.RegisterSuccess,
    val passwordRegisterValidation: RegisterValidation = RegisterValidation.RegisterSuccess
)
