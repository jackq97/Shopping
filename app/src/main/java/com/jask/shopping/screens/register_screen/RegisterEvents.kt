package com.jask.shopping.screens.register_screen

sealed class RegisterEvents {

    data class CreateAccountWithEmailAndPassword(val email: String,
                                                 val password: String,
                                                 val firstName: String,
                                                 val lastName: String
    ): RegisterEvents()
}