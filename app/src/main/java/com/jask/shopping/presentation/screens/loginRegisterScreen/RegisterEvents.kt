package com.jask.shopping.presentation.screens.loginRegisterScreen

import com.jask.shopping.data.UserRegisterForum

sealed class RegisterEvents {

    data class CreateAccountWithEmailAndPassword(val userRegisterForum: UserRegisterForum,val password: String)
}