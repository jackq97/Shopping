package com.jask.shopping.screens.cart_screen

import androidx.paging.PagingData
import com.jask.shopping.data.model.CartProduct
import com.jask.shopping.data.model.Product
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow

data class CartStates(
    val isLoading: Boolean = false,
    val isSuccess: Boolean = false,
    val cartProduct: List<CartProduct> = listOf(CartProduct()),
    val isError: String? = "",
)