package com.jask.shopping.presentation.screens.login_screen

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.jask.shopping.navigation.Screens
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginScreen(
                navController: NavController,
                state: LoginState,
                onSignInClick: () -> Unit
                ){


    val sheetState = rememberModalBottomSheetState()
    val scope = rememberCoroutineScope()
    var showBottomSheet by remember { mutableStateOf(false) }

    if (showBottomSheet) {
        ModalBottomSheet(
            modifier = Modifier.height(300.dp),
            onDismissRequest = {
                showBottomSheet = false
            },
            sheetState = sheetState
        ) {
            // Sheet content
            Button(onClick = {
                scope.launch { sheetState.hide() }.invokeOnCompletion {
                    if (!sheetState.isVisible) {
                        showBottomSheet = false
                    }
                }
            }) {
                Text("Hide bottom sheet")
            }
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

            TextField(value = "Email", onValueChange = {})
            Spacer(modifier = Modifier.height(10.dp))
            TextField(value = "Password",
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

            Button(onClick = { /*TODO*/ }) {
                Text(text = "Login")
            }

            Spacer(modifier = Modifier.height(10.dp))

            Row {
                Button(onClick = { /*TODO*/ }) {
                    Text(text = "Facebook")
                }

                Spacer(modifier = Modifier.width(10.dp))

                Button(onClick = onSignInClick) {
                    Text(text = "Google")
                }
            }
        }
    }
}

@Composable
@Preview
fun LoginScreenPreview(){
LoginScreen(navController = rememberNavController(),
    state = LoginState(),
    onSignInClick = {})
}

