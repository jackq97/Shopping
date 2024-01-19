package com.jask.shopping.presentation.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.jask.shopping.navigation.Screens

@Composable
fun LoginRegisterScreen(navController: NavController){

    Surface(modifier = Modifier.fillMaxSize()) {
        Column(horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
            ) {

            Button(onClick = { navController.navigate(Screens.RegisterScreen.route) }) {
             Text(text = "Register")
            }
            Button(onClick = { navController.navigate(Screens.LoginScreen.route) }) {
                Text(text = "Log in")
            }
        }
    }
}

@Composable
@Preview()
fun LoginRegisterScreenPreview(){

    LoginRegisterScreen(navController = rememberNavController())
}