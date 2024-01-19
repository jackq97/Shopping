package com.jask.shopping.presentation.screens.register_screen

import com.jask.shopping.data.UserData

sealed class RegisterEvents {

    data class CreateAccountWithEmailAndPassword(val email: String,val password: String): RegisterEvents()
}