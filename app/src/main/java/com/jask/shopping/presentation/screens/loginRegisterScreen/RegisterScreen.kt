package com.jask.shopping.presentation.screens.loginRegisterScreen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun RegisterScreen(state: RegisterStates){

    var firstName by rememberSaveable { mutableStateOf("First Name") }
    var lastName by rememberSaveable { mutableStateOf("Last Name") }
    var email by rememberSaveable { mutableStateOf("Email") }
    var password by rememberSaveable { mutableStateOf("Password") }

    Surface(modifier = Modifier.fillMaxSize()) {
        Column(verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
            ) {

            Text(text = "Let's register")
            Row(verticalAlignment = Alignment.CenterVertically) {

                Text(text = "Do you have an account?")
                TextButton(onClick = { /*TODO*/ }) {
                    Text(text = "Log in")
                }
            }

            OutlinedTextField(value = firstName, onValueChange = { firstName = it })
            OutlinedTextField(value = lastName, onValueChange = { lastName = it })
            OutlinedTextField(value = email, onValueChange = { email = it })
            OutlinedTextField(value = password, onValueChange = { password = it })
            
            Spacer(modifier = Modifier.height(10.dp))

            Button(onClick = { /*TODO*/ }) {
                Text(text = "Register")
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
fun RegisterScreenPreview(){
    RegisterScreen(state = RegisterStates())
}