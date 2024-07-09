package com.jask.shopping.screens.order_screen

import androidx.paging.PagingData
import com.jask.shopping.data.model.Address
import com.jask.shopping.data.model.CartProduct
import com.jask.shopping.data.model.Order
import com.jask.shopping.data.model.Product
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow

data class OrderStates(
    val isLoading: Boolean = false,
    val isSuccess: Boolean = false,
    val order: List<Order> = listOf(Order(
        orderStatus = "",
        totalPrice = 0.0f,
        products = listOf(),
        address = Address(),
        date = "",
        orderId = 0L
    )),
    val isError: String? = "",
)