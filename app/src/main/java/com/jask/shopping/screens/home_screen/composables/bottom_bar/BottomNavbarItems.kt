package com.jask.shopping.screens.home_screen.composables.bottom_bar

import com.jask.shopping.R

sealed class BottomNavigationItem(var route: String, var icon: Int, var title: String) {
    data object HomeFeedScreen : BottomNavigationItem("home_feed_screen/{id}", R.drawable.home, "home")
    data object SearchScreen : BottomNavigationItem("search_screen", R.drawable.search, "Search")
    data object CartScreen : BottomNavigationItem("cart_screen", R.drawable.shopping_bag, "Cart")
    data object ProfileScreen : BottomNavigationItem("profile_screen", R.drawable.profile, "Profile")
}