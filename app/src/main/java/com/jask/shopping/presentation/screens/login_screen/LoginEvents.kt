package com.jask.shopping.presentation.screens.login_screen

sealed class LoginEvents {

    data class SendPasswordResetEmail(val email: String): LoginEvents()
}