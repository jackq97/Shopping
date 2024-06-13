package com.jask.shopping.screens.login_screen

sealed class LoginEvents {

    data class SendPasswordResetEmail(val email: String): LoginEvents()
}