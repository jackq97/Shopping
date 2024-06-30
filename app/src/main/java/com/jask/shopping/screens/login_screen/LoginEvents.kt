package com.jask.shopping.screens.login_screen

sealed class LoginEvents {

    data class SendPasswordResetEmail(val email: String): LoginEvents()
    data class SignInUser(val email: String,
                          val password: String
    ): LoginEvents()
    data object ResetState : LoginEvents()
}