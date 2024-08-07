package com.jask.shopping

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.result.IntentSenderRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.lifecycleScope
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.google.android.gms.auth.api.identity.Identity
import com.jask.shopping.navigation.Screens
import com.jask.shopping.screens.login_register_screen.LoginRegisterScreen
import com.jask.shopping.screens.home_screen.HomeScreen
import com.jask.shopping.screens.login_screen.GoogleAuthUiClient
import com.jask.shopping.screens.login_screen.LoginScreen
import com.jask.shopping.screens.login_screen.LoginViewModel
import com.jask.shopping.screens.register_screen.RegisterScreen
import com.jask.shopping.screens.register_screen.RegisterViewModel
import com.jask.shopping.ui.theme.ShoppingTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val googleAuthUiClient by lazy {
        GoogleAuthUiClient(
            context = applicationContext,
            oneTapClient = Identity.getSignInClient(applicationContext)
        )
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ShoppingTheme {

                val mainNavController = rememberNavController()
                var startDestination by remember { mutableStateOf(Screens.LoginRegisterScreen.route) }

                if(googleAuthUiClient.getSignedInUser() != null) {
                    startDestination = Screens.HomeScreen.route
                }

                NavHost(
                    modifier = Modifier.background(MaterialTheme.colorScheme.background),
                    navController = mainNavController,
                    startDestination = startDestination
                ) {
                    composable(route = Screens.LoginRegisterScreen.route) {
                        LoginRegisterScreen(navController = mainNavController) }

                    composable(route = Screens.LoginScreen.route) {

                        val loginViewModel: LoginViewModel = hiltViewModel()
                        val state = loginViewModel.state.value

                        val launcher = rememberLauncherForActivityResult(
                            contract = ActivityResultContracts.StartIntentSenderForResult(),
                            onResult = { result ->
                                if(result.resultCode == RESULT_OK) {
                                    lifecycleScope.launch {
                                        val signInResult = googleAuthUiClient.signInWithIntent(
                                            intent = result.data ?: return@launch
                                        )
                                        loginViewModel.onSignInResult(signInResult)
                                        loginViewModel.uploadUserDataWithGoogleSignIn(signInResult)
                                    }
                                }
                            }
                        )

                        LaunchedEffect(key1 = state.isSignInSuccessful) {
                            if(state.isSignInSuccessful) {
                                Toast.makeText(
                                    applicationContext,
                                    "Sign in successful",
                                    Toast.LENGTH_LONG
                                ).show()
                                mainNavController.navigate(Screens.HomeScreen.route)
                                loginViewModel.resetState()
                            }
                        }

                        LoginScreen(
                            navController = mainNavController,
                            state = state,
                            onSignInClick = {
                                Log.d("TAG", "onCreate: sign in google")
                                lifecycleScope.launch {
                                    val signInIntentSender = googleAuthUiClient.signIn()
                                    launcher.launch(
                                        IntentSenderRequest.Builder(
                                            signInIntentSender ?: return@launch
                                        ).build()
                                    )
                                }
                            },
                            onEvent = loginViewModel::onEvent,
                        ) }

                    composable(route = Screens.RegisterScreen.route) {

                        val registerViewModel: RegisterViewModel = hiltViewModel()
                        val state = registerViewModel.state.value

                        RegisterScreen(state = state,
                            navController = mainNavController,
                            onEvent = registerViewModel::onEvent
                        )
                    }

                    composable(route = Screens.HomeScreen.route) {
                        HomeScreen(mainNavController = mainNavController)
                    }
                }
            }
        }
    }
}