package com.jask.shopping.presentation.screens.home_feed

import com.jask.shopping.data.model.Product

data class HomeFeedStates(
    val product: List<Product> = emptyList()
)