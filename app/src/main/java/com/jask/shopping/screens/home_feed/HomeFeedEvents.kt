package com.jask.shopping.screens.home_feed

import com.jask.shopping.data.model.CartProduct
import com.jask.shopping.screens.product_view_screen.ProductViewEvents

sealed class HomeFeedEvents {
    data object GetCartProductData: HomeFeedEvents()
    data class AddUpdateProduct(val cartProduct: CartProduct): HomeFeedEvents()
}