package com.jask.shopping.screens.login_register_screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.jask.shopping.navigation.Screens

@Composable
fun LoginRegisterScreen(navController: NavController){

    Surface(modifier = Modifier.fillMaxSize()) {


        Column(horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
            ) {

            LoginRegisterButton(label = "Register") {
                navController.navigate(Screens.RegisterScreen.route)
            }

            LoginRegisterButton(label = "Login") {
                navController.navigate(Screens.LoginScreen.route)
            }
        }
    }
}

@Composable
fun LoginRegisterButton(
    label: String,
    onClick: () -> Unit
) {
    Button(
        modifier = Modifier.width(200.dp),
        onClick = onClick) {
        Text(text = label)
    }
}

@Composable
@Preview
fun LoginRegisterScreenPreview(){

    LoginRegisterScreen(navController = rememberNavController())
}