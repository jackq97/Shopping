package com.jask.shopping.data.model

data class Product(
    val id: String,
    val name: String,
    val category: String,
    val dealType: String,
    val price: Float,
    val offerPercentage: Float? = null,
    val description: String? = null,
    val colors: List<String> = listOf(""),
    val sizes: List<String> = listOf(""),
    val images: List<String>
){
    constructor(): this("0", "", "", "",0f, images = emptyList())
}