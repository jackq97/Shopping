package com.jask.shopping.navigation

import androidx.compose.foundation.background
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.paging.compose.collectAsLazyPagingItems
import com.jask.shopping.presentation.screens.cart_screen.CartScreen
import com.jask.shopping.presentation.screens.home_feed.HomeFeedScreen
import com.jask.shopping.presentation.screens.home_feed.HomeFeedViewModel
import com.jask.shopping.presentation.screens.home_screen.composables.bottom_bar.BackPressHandler
import com.jask.shopping.presentation.screens.home_screen.composables.bottom_bar.BottomNavigationItem
import com.jask.shopping.presentation.screens.product_view_screen.ProductViewScreen
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
        startDestination = Screens.ProductViewScreen.route
    ) {


        //bottom bar
        composable(route = BottomNavigationItem.HomeFeedScreen.route){

            val homeFeedViewModel: HomeFeedViewModel = hiltViewModel()
            val state = homeFeedViewModel.state.value

            BackPressHandler(onBackPressed = {})
            HomeFeedScreen(
                state = state,
                onEvent = homeFeedViewModel::onEvent
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
            BackPressHandler(onBackPressed = {})
            ProductViewScreen()
        }
    }
}