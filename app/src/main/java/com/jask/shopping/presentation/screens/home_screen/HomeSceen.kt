package com.jask.shopping.presentation.screens.home_screen

import android.util.Log
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.jask.shopping.navigation.MyBottomNavigation
import com.jask.shopping.presentation.screens.home_screen.composables.bottom_bar.BackPressHandler
import com.jask.shopping.presentation.screens.home_screen.composables.bottom_bar.BottomNavigationBar

@Composable
fun HomeScreen(
    onSignOut: () -> Unit
){
    val navController = rememberNavController()
    Scaffold(
        bottomBar = {
            BottomNavigationBar(navController = navController,
                bottomBarState = true)
        },

        content = {
            /*Surface(modifier = Modifier
                .fillMaxSize()
                .padding(it)) {

                Column(verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {

                    Text(text = "You Logged in")
                    Button(onClick = onSignOut) {
                        Text(text = "Log out")
                    }
                }
            }*/
            MyBottomNavigation(
                navController = navController,
                modifier = Modifier.padding(it)
            )
        }
    )
}

@Composable
@Preview
fun HomeScreenPreview(){
    HomeScreen(
        onSignOut = {}
    )}