package com.jask.shopping.data.model

data class UserData(
    val firstName: String,
    val lastName: String,
    val email: String,
){
    constructor() : this("","","")
}