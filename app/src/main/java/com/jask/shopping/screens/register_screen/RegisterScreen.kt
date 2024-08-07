package com.jask.shopping.screens.register_screen

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
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
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.jask.shopping.navigation.Screens
import com.jask.shopping.util.RegisterValidation

@Composable
fun RegisterScreen(
    state: RegisterStates,
    navController: NavController,
    onEvent: (RegisterEvents) -> Unit
){

    var firstName by rememberSaveable { mutableStateOf("") }
    var lastName by rememberSaveable { mutableStateOf("") }
    var email by rememberSaveable { mutableStateOf("") }
    var password by rememberSaveable { mutableStateOf("") }
    var emailValidationError by rememberSaveable { mutableStateOf(false) }
    var emailErrorText by rememberSaveable { mutableStateOf("") }
    var passwordValidationError by rememberSaveable { mutableStateOf(false) }
    var passwordErrorText by rememberSaveable { mutableStateOf("") }

    if (state.emailRegisterValidation is RegisterValidation.Failed){
    emailValidationError = true
    emailErrorText = state.emailRegisterValidation.message }

    if (state.passwordRegisterValidation is RegisterValidation.Failed){
        passwordValidationError = true
        passwordErrorText = state.passwordRegisterValidation.message }

    Surface(modifier = Modifier.fillMaxSize()) {
        Column(verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
            ) {

            Text(text = "Let's register")
            Row(verticalAlignment = Alignment.CenterVertically) {

                Text(text = "Do you have an account?")
                TextButton(onClick = { navController.navigate(Screens.LoginScreen.route) }) {
                    Text(text = "Log in")
                }
            }

            OutlinedTextField(value = firstName,
                label = { Text(text = "First Name")},
                onValueChange = { firstName = it })

            Spacer(modifier = Modifier.height(17.dp))

            OutlinedTextField(value = lastName,
                label = { Text(text = "Last Name")},
                onValueChange = { lastName = it })

            Spacer(modifier = Modifier.height(18.dp))

            OutlinedTextField(value = email,
                label = { Text(text = "Email")},
                onValueChange = { email = it },
                isError = emailValidationError,
                supportingText = {if (emailValidationError)
                    Text(text = emailErrorText)
                 })

            OutlinedTextField(value = password,
                label = { Text(text = "password")},
                onValueChange = { password = it },
                isError = passwordValidationError,
                supportingText = {if (passwordValidationError)
                    Text(text = passwordErrorText)
                })

            Spacer(modifier = Modifier.height(10.dp))

            if (state.isLoading) {
                CircularProgressIndicator()
            } else {
                Button(onClick = {
                    emailValidationError = false
                    passwordValidationError = false
                    if (firstName.isNotEmpty() && lastName.isNotEmpty() && email.isNotEmpty() && password.isNotEmpty()) {
                        Log.d("register screen ", " Registered ")
                        onEvent(RegisterEvents.CreateAccountWithEmailAndPassword(
                            email = email.trim(),
                            password = password,
                            firstName = firstName,
                            lastName = lastName
                        )
                        )
                    } else {
                        Log.d("register screen ", "RegisterScreen: fill all fields")
                    }
                }) {
                    Text(text = "Register")
                }
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
    RegisterScreen(state = RegisterStates(),
        navController = rememberNavController(),
        onEvent = {},
    )
}