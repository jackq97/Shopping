package com.jask.shopping.screens.cart_screen

sealed class CartEvents {
    data class IncreaseQuantity(val documentId: String): CartEvents()
    data class DecreaseQuantity(val documentId: String): CartEvents()
}