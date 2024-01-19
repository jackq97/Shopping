package com.jask.shopping.presentation.screens.register_screen

data class RegisterStates(
    val isLoading: Boolean = false,
    val isSuccess: String? = "",
    val isError: String? = ""
)