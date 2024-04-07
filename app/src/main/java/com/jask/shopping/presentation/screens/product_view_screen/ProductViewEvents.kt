package com.jask.shopping.presentation.screens.product_view_screen

sealed class ProductViewEvents {
    data class GetProductsByCategory(val category: String): ProductViewEvents()
}