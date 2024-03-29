package com.jask.shopping.navigation

import androidx.compose.foundation.background
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.jask.shopping.presentation.screens.cart_screen.CartScreen
import com.jask.shopping.presentation.screens.home_feed.HomeFeedScreen
import com.jask.shopping.presentation.screens.home_feed.HomeFeedViewModel
import com.jask.shopping.presentation.screens.home_screen.composables.bottom_bar.BackPressHandler
import com.jask.shopping.presentation.screens.home_screen.composables.bottom_bar.BottomNavigationItem
import com.jask.shopping.presentation.screens.product_view_screen.ProductViewScreen
import com.jask.shopping.presentation.screens.product_view_screen.ProductViewStates
import com.jask.shopping.presentation.screens.product_view_screen.ProductViewViewModel
import com.jask.shopping.presentation.screens.profile_screen.ProfileScreen
import com.jask.shopping.presentation.screens.search_screen.SearchScreen


@Composable
fun MyBottomNavigation(
    modifier: Modifier,
    navController: NavHostController
) {

    NavHost(
        modifier = modifier.background(MaterialTheme.colorScheme.background),
        navController = navController,
        startDestination = BottomNavigationItem.HomeFeedScreen.route
    ) {


        //bottom bar
        composable(route = BottomNavigationItem.HomeFeedScreen.route,
            arguments = listOf(
            navArgument("index"){
                type = NavType.StringType
            }
        )){

            val homeFeedViewModel: HomeFeedViewModel = hiltViewModel()
            val state = homeFeedViewModel.state.value

            BackPressHandler(onBackPressed = {})
            HomeFeedScreen(
                state = state,
                onEvent = homeFeedViewModel::onEvent,
                navController = navController
            )
        }

        composable(route = BottomNavigationItem.SearchScreen.route){
            BackPressHandler(onBackPressed = {})
            SearchScreen()
        }

        composable(route = BottomNavigationItem.CartScreen.route){
            BackPressHandler(onBackPressed = {})
            CartScreen()
        }

        composable(route = BottomNavigationItem.ProfileScreen.route){
            BackPressHandler(onBackPressed = {})
            ProfileScreen()
        }

        composable(route = Screens.ProductViewScreen.route){
            val index = it.arguments!!.getString("index")

            val productViewViewModel: ProductViewViewModel = hiltViewModel()
            val state = productViewViewModel.state.value

            ProductViewScreen(index = index!!,
                state = state,
                onEvent = {},

            )
        }
    }
}