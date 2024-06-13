package com.jask.shopping.screens.product_view_screen

sealed class ProductViewEvents {
    data class GetProductsByCategory(val category: String): ProductViewEvents()
}