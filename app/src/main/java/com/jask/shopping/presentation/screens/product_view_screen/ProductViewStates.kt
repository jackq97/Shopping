package com.jask.shopping.presentation.screens.product_view_screen

import com.jask.shopping.data.model.Product

data class ProductViewStates(
    val specialProduct: List<Product> = listOf(),
    /*val bestDeals: Flow<PagingData<Product>> = emptyFlow(),
    val bestProducts : Flow<PagingData<Product>> = emptyFlow()*/
)