package com.jask.shopping.screens.login_screen

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.jask.shopping.navigation.Screens
import com.jask.shopping.util.RegisterValidation

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginScreen(
    navController: NavController,
    state: LoginState,
    onSignInClick: () -> Unit,
    onEvent: (LoginEvents) -> Unit){

    val sheetState = rememberModalBottomSheetState()
    var showBottomSheet by remember { mutableStateOf(false) }

    var resetEmail by rememberSaveable { mutableStateOf("") }
    var emailErrorText by rememberSaveable { mutableStateOf("") }
    var emailValidationError by rememberSaveable { mutableStateOf(false) }

    if (state.emailRegisterValidation is RegisterValidation.Failed){
        emailValidationError = true
        emailErrorText = state.emailRegisterValidation.message
    }

    if (state.isSuccess){
        showBottomSheet = false
    }

    if (showBottomSheet) {
        ModalBottomSheet(
            modifier = Modifier.wrapContentSize(),
            onDismissRequest = {
                showBottomSheet = false
            },
            sheetState = sheetState
        ) {

            // Sheet content
            Row(verticalAlignment = Alignment.CenterVertically) {
                
                Spacer(modifier = Modifier.width(8.dp))

                    OutlinedTextField(
                        modifier = Modifier.weight(7f),
                        value = resetEmail,
                        label = { Text(text = "Email")},
                        shape = RoundedCornerShape(16.dp),
                        isError = emailValidationError,
                        supportingText = {if (emailValidationError)
                            Text(text = emailErrorText)
                        },
                        onValueChange = { resetEmail = it })

                if (state.isLoading){
                    CircularProgressIndicator()
                } else {
                    IconButton(
                        modifier = Modifier
                            .weight(1.5f),
                        onClick = {
                            if (resetEmail.isNotEmpty()) {
                                onEvent(
                                    LoginEvents.SendPasswordResetEmail(email = resetEmail)
                                )
                            } else {
                                TODO("show dialog when field is empty")
                            }
                        }) {
                        Icon(imageVector = Icons.Default.Check, contentDescription = "send")
                    }
                }
            }

            Spacer(modifier = Modifier.height(50.dp))

        }
    }

    Surface(modifier = Modifier.fillMaxSize()) {

        val context = LocalContext.current
        LaunchedEffect(key1 = state.signInError) {
            state.signInError?.let { error ->
                Toast.makeText(
                    context,
                    error,
                    Toast.LENGTH_LONG
                ).show()
            }
        }

        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
            ) {

            Text(text = "LETS LOGIN")
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(text = "don't have an account?")
                TextButton(onClick = { navController.navigate(Screens.RegisterScreen.route) }) {
                    Text(text = "Register")
                }
            }

            LoginTextFields(label = "Email",
                text = "",
                onValueChange = {})
            Spacer(modifier = Modifier.height(10.dp))
            LoginTextFields(label = "Password",
                text = "",
                onValueChange = {})

            Row(modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Start) {

                Spacer(modifier = Modifier.width(57.dp))

                TextButton(
                    modifier = Modifier
                        .height(35.dp),
                    onClick = { showBottomSheet = true }) {
                    Text(
                        text = "Forgot password")
                }
            }

            Spacer(modifier = Modifier.height(10.dp))

            SignInButton(label = "Login",
                onSignInClick = {})

            Spacer(modifier = Modifier.height(10.dp))

            Row {
                SignInButton(label = "Facebook",
                    onSignInClick = {})

                Spacer(modifier = Modifier.width(10.dp))

                SignInButton(label = "Google",
                    onSignInClick = onSignInClick)
            }
        }
    }
}

@Composable
fun SignInButton(
    label: String,
    onSignInClick: () -> Unit){

    Button(
        modifier = Modifier.width(120.dp),
        onClick = onSignInClick) {
        Text(text = label)
    }
}

@Composable
fun LoginTextFields(
    label: String,
    text: String,
    onValueChange: (String) -> Unit
){

    OutlinedTextField(
        value = text,
        onValueChange = onValueChange,
        label = { Text(label) }
    )
}

@Composable
@Preview
fun LoginScreenPreview(){
LoginScreen(navController = rememberNavController(),
    state = LoginState(),
    onSignInClick = {},
    onEvent = {})
}