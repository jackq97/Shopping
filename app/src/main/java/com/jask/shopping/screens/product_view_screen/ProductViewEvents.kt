package com.jask.shopping.screens.product_view_screen

import com.jask.shopping.data.model.CartProduct

sealed class ProductViewEvents {
    data class GetProductsByCategory(val category: String): ProductViewEvents()
    data class AddUpdateProduct(val cartProduct: CartProduct): ProductViewEvents()
}