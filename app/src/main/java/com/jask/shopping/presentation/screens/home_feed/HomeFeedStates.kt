package com.jask.shopping.presentation.screens.home_feed

import com.jask.shopping.data.model.Product

data class HomeFeedStates(
    val specialProduct: List<Product>? = emptyList(),
    val bestDeals: List<Product>? = emptyList(),
    val bestProducts : List<Product>? = emptyList()
)