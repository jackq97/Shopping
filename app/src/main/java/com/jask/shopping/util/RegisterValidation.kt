package com.jask.shopping.util

sealed class RegisterValidation(){
    data object RegisterSuccess: RegisterValidation()
    data class Failed(val message: String): RegisterValidation()
}

data class RegisterFieldsState(
    val email: RegisterValidation,
    val password: RegisterValidation
)