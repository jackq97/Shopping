package com.jask.shopping.screens.billing_screen

import com.jask.shopping.data.model.Order

sealed class BillingEvents {
    data class PlaceOrder(val order: Order): BillingEvents()
    data object RemoveCartCollection: BillingEvents()
    data object GetCartProducts: BillingEvents()
}