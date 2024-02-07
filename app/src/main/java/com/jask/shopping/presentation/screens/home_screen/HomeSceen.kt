package com.jask.shopping.presentation.screens.home_screen

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.rememberNavController
import com.jask.shopping.navigation.Screens
import com.jask.shopping.presentation.screens.home_screen.composables.bottom_bar.BottomNavigationBar

@Composable
fun HomeScreen(
    onSignOut: () -> Unit
){

    Scaffold(
        bottomBar = {
            BottomNavigationBar(navController = rememberNavController(),
                bottomBarState = true)
        },

        content = {
            Surface(modifier = Modifier
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
            }
        }
    )
}

@Composable
@Preview
fun HomeScreenPreview(){
    HomeScreen {
    }
}