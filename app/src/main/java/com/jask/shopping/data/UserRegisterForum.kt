package com.jask.shopping.data

data class UserRegisterForum(
    val firstName: String,
    val lastName: String,
    val email: String,
    val imagePath: String = "",
){
    constructor(): this("", "", "", "")
}
