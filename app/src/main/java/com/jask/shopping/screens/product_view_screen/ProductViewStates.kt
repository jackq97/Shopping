package com.jask.shopping.screens.product_view_screen

import com.jask.shopping.data.model.Product

data class ProductViewStates(
    val product: Product? = null,
    val isLoading: Boolean = false,
    val isAddProductLoading: Boolean = false,
    var isSuccess: Boolean = false,
    var isAddProductSuccess: Boolean = false
)