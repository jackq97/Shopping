package com.jask.shopping.data.model

data class CartProduct(
    val product: Product,
    val quantity: Int,
    val selected: Int? = null,
    val selectedSize: String? = null
) {
    constructor(): this(Product(), 1, null, null)
}
