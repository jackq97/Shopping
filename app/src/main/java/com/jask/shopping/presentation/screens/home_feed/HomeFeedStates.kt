package com.jask.shopping.presentation.screens.home_feed

import androidx.paging.PagingData
import com.jask.shopping.data.model.Product
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow

data class HomeFeedStates(
    val specialProduct: Flow<PagingData<Product>> = emptyFlow(),
    val bestDeals: Flow<PagingData<Product>> = emptyFlow(),
    val bestProducts : Flow<PagingData<Product>> = emptyFlow()
)