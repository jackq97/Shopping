package com.jask.shopping.screens.product_view_screen

import com.jask.shopping.data.model.Product

data class ProductViewStates(
    val product: Product = Product(),
    val isLoading: Boolean = false
)