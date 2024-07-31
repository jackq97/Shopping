package com.jask.shopping.screens.cart_screen

import com.jask.shopping.data.model.CartProduct

data class CartStates(
    val isLoading: Boolean = false,
    val isSuccess: Boolean = false,
    val cartProduct: List<CartProduct>? = null,
    val isError: String? = "",
)