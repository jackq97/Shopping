package com.jask.shopping.data

data class UserData(
    val firstName: String,
    val lastName: String,
    val email: String,
    val imagePath: String = "",
){
    constructor(): this("", "", "", "")
}
