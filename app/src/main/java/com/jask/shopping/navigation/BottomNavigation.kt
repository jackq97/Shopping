package com.jask.shopping.navigation

import android.util.Log
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.jask.shopping.presentation.screens.cart_screen.CartScreen
import com.jask.shopping.presentation.screens.home_feed.HomeFeedScreen
import com.jask.shopping.presentation.screens.home_screen.composables.bottom_bar.BackPressHandler
import com.jask.shopping.presentation.screens.home_screen.composables.bottom_bar.BottomNavigationItem
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
        composable(route = BottomNavigationItem.HomeFeedScreen.route){
            BackPressHandler(onBackPressed = {})
            HomeFeedScreen()
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
    }
}