package com.jask.shopping.screens.order_details_screen

import com.jask.shopping.data.model.Order

sealed class OrderEvents {
    data class GetOrderById(val id: String): OrderEvents()
}