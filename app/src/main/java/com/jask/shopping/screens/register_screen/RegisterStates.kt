package com.jask.shopping.screens.register_screen

import com.jask.shopping.util.RegisterValidation

data class RegisterStates(
    val isLoading: Boolean = false,
    val isSuccess: String? = "",
    val isError: String? = "",
    val emailRegisterValidation: RegisterValidation = RegisterValidation.RegisterSuccess,
    val passwordRegisterValidation: RegisterValidation = RegisterValidation.RegisterSuccess,
)