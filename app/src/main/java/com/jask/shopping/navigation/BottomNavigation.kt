package com.jask.shopping.navigation

import androidx.compose.foundation.background
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.jask.shopping.screens.address_screen.AddressScreen
import com.jask.shopping.screens.address_screen.AddressViewModel
import com.jask.shopping.screens.billing_screen.BillingScreen
import com.jask.shopping.screens.billing_screen.BillingViewModel
import com.jask.shopping.screens.cart_screen.CartScreen
import com.jask.shopping.screens.cart_screen.CartViewModel
import com.jask.shopping.screens.home_feed.HomeFeedScreen
import com.jask.shopping.screens.home_feed.HomeFeedViewModel
import com.jask.shopping.screens.home_screen.composables.bottom_bar.BackPressHandler
import com.jask.shopping.screens.home_screen.composables.bottom_bar.BottomNavigationItem
import com.jask.shopping.screens.order_details_screen.OrderDetailsScreen
import com.jask.shopping.screens.order_details_screen.OrderDetailsViewModel
import com.jask.shopping.screens.order_screen.OrderScreen
import com.jask.shopping.screens.order_screen.OrderViewModel
import com.jask.shopping.screens.product_view_screen.ProductViewScreen
import com.jask.shopping.screens.product_view_screen.ProductViewViewModel
import com.jask.shopping.screens.profile_screen.ProfileScreen
import com.jask.shopping.screens.profile_screen.ProfileScreenViewModel
import com.jask.shopping.screens.search_screen.SearchScreen


@Composable
fun MyBottomNavigation(
    modifier: Modifier,
    navController: NavHostController,
    mainNavController: NavController
) {

    val productViewViewModel: ProductViewViewModel = hiltViewModel()
    val productState = productViewViewModel.state.value
    val profileScreenViewmodel: ProfileScreenViewModel = hiltViewModel()
    val homeFeedViewModel: HomeFeedViewModel = hiltViewModel()
    val homeFeedState = homeFeedViewModel.state.value

    NavHost(
        modifier = modifier.background(MaterialTheme.colorScheme.background),
        navController = navController,
        startDestination = BottomNavigationItem.HomeFeedScreen.route
    ) {

        //bottom bar
        composable(route = BottomNavigationItem.HomeFeedScreen.route,
            arguments = listOf(navArgument("id"){
                type = NavType.StringType
            })
        ){
            BackPressHandler(onBackPressed = {})
            HomeFeedScreen(
                state = homeFeedState,
                onEvent = homeFeedViewModel::onEvent
            ){id ->
                navController.navigate("product_view_screen/${id}")
            }
        }

        composable(route = BottomNavigationItem.SearchScreen.route){
            BackPressHandler(onBackPressed = {})
            SearchScreen()
        }

        composable(route = BottomNavigationItem.CartScreen.route){
            BackPressHandler(onBackPressed = {})
            val viewModel: CartViewModel = hiltViewModel()
            val state = viewModel.state.value
            CartScreen(state = state,
                onEvent = viewModel::onEvent,
                navController = navController
                )
        }

        composable(route = BottomNavigationItem.ProfileScreen.route){
            BackPressHandler(onBackPressed = {})
            ProfileScreen(
                onEvent = profileScreenViewmodel::onEvent,
                mainNavController = mainNavController,
                navController = navController
            )
        }

        composable(route = Screens.ProductViewScreen.route){
            val id = it.arguments?.getString("id") ?: ""
            ProductViewScreen(
                id = id,
                state = productState,
                onEvent = productViewViewModel::onEvent
            )
        }

        composable(route = Screens.AddressScreen.route){
            val viewModel: AddressViewModel = hiltViewModel()
            AddressScreen(onEvent = viewModel::onEvent)
        }

        composable(route = Screens.BillingScreen.route){
            val viewModel: BillingViewModel = hiltViewModel()
            val state = viewModel.state.value
            BillingScreen(navController = navController,
                states = state,
                onEvent = viewModel::onEvent
            )
        }

        composable(route = Screens.OrderScreen.route,
            arguments = listOf(navArgument("id"){
                type = NavType.StringType
            })
        ){
            val viewModel: OrderViewModel = hiltViewModel()
            val state = viewModel.state.value
            OrderScreen(states = state,
                navController = navController
            )
        }

        composable(route = Screens.OrderDetailsScreen.route){
            val id = it.arguments?.getString("id") ?: ""
            val viewModel: OrderDetailsViewModel = hiltViewModel()
            val state = viewModel.state.value
            OrderDetailsScreen(states = state,
                id = id,
                onEvent = viewModel::onEvent
            )
        }
    }
}