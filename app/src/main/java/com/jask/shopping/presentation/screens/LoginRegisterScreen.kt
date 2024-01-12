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

@Composable
fun LoginRegisterScreen(){

    Surface(modifier = Modifier.fillMaxSize()) {
        Column(horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
            ) {

            Button(onClick = { /*TODO*/ }) {
             Text(text = "Register")
            }
            Button(onClick = { /*TODO*/ }) {
                Text(text = "Log in")
            }
        }
    }
}

@Composable
@Preview()
fun LoginRegisterScreenPreview(){

    LoginRegisterScreen()
}