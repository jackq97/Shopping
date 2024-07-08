package com.jask.shopping.screens.billing_screen

import androidx.paging.PagingData
import com.jask.shopping.data.model.Address
import com.jask.shopping.data.model.CartProduct
import com.jask.shopping.data.model.Product
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow

data class BillingStates(
    val isLoading: Boolean = false,
    val isSuccess: Boolean = false,
    val cartProduct: List<CartProduct> = listOf(CartProduct()),
    val addressList: List<Address> = listOf(Address()),
    val addressTitle: List<String> = listOf(""),
    val isError: String? = "",
)