package com.jask.shopping.screens.order_details_screen

import com.jask.shopping.data.model.CartProduct
import com.jask.shopping.data.model.Order
import com.jask.shopping.data.model.Product

data class OrderDetailsStates(
    val isLoading: Boolean = false,
    val isSuccess: Boolean = false,
    val order: Order? = null,
    val isError: String? = "",
)