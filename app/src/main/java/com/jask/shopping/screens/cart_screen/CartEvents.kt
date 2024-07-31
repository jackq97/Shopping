package com.jask.shopping.screens.cart_screen

import com.jask.shopping.data.model.Order

sealed class CartEvents {
    data class IncreaseQuantity(val documentId: String): CartEvents()
    data class DecreaseQuantity(val documentId: String): CartEvents()
    data class PlaceOrder(val order: Order): CartEvents()
    data object GetCardProducts : CartEvents()
}