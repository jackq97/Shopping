package com.jask.shopping.screens.home_screen

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.jask.shopping.navigation.MyBottomNavigation
import com.jask.shopping.screens.home_screen.composables.bottom_bar.BottomNavigationBar

@Composable
fun HomeScreen(
    mainNavController: NavController
){

    val bottomNavController = rememberNavController()

    Scaffold(
        bottomBar = {
            BottomNavigationBar(navController = bottomNavController,
                bottomBarState = true)
        },

        content = {

            MyBottomNavigation(
                navController = bottomNavController,
                mainNavController = mainNavController,
                modifier = Modifier.padding(it)
            )
        }
    )
}

@Composable
@Preview
fun HomeScreenPreview(){
    HomeScreen(mainNavController = rememberNavController())
}