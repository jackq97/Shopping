package com.jask.shopping.presentation.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun LoginScreen(){

    Surface(modifier = Modifier.fillMaxSize()) {

        Column(horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
            ) {

            Text(text = "LETS LOGIN")
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(text = "don't have an account?")
                TextButton(onClick = { /*TODO*/ }) {
                    Text(text = "Register")
                }
            }

            TextField(value = "Email", onValueChange = {})
            TextField(value = "Password", onValueChange = {})

            Spacer(modifier = Modifier.height(10.dp))

            Text(text = "Forgot Password")

            Button(onClick = { /*TODO*/ }) {
                Text(text = "Login")
            }

            Row {
                Button(onClick = { /*TODO*/ }) {
                    Text(text = "Facebook")
                }

                Spacer(modifier = Modifier.width(10.dp))

                Button(onClick = { /*TODO*/ }) {
                    Text(text = "Google")
                }
            }
        }
    }
}

@Composable
@Preview
fun LoginScreenPreview(){
LoginScreen()
}

