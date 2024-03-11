package com.jask.shopping.navigation

sealed class Screens(val route: String) {
    data object LoginRegisterScreen: Screens(route = "login_register_screen")
    data object LoginScreen: Screens(route = "login_screen")
    data object RegisterScreen: Screens(route = "register_screen")
    data object HomeScreen: Screens(route = "home_screen")
    data object ProductViewScreen: Screens(route = "product_view_screen")
}