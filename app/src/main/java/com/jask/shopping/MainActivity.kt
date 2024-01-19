package com.jask.shopping

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.result.IntentSenderRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.google.android.gms.auth.api.identity.Identity
import com.jask.shopping.navigation.Screens
import com.jask.shopping.presentation.screens.LoginRegisterScreen
import com.jask.shopping.presentation.screens.login_screen.GoogleAuthUiClient
import com.jask.shopping.presentation.screens.login_screen.LoginScreen
import com.jask.shopping.presentation.screens.login_screen.LoginViewModel
import com.jask.shopping.presentation.screens.register_screen.RegisterScreen
import com.jask.shopping.presentation.screens.register_screen.RegisterStates
import com.jask.shopping.presentation.screens.register_screen.RegisterViewmodel
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
                val navController: NavHostController = rememberNavController()

                NavHost(
                    modifier = Modifier.background(MaterialTheme.colorScheme.background),
                    navController = navController,
                    startDestination = Screens.LoginRegisterScreen.route
                ) {
                    composable(route = Screens.LoginRegisterScreen.route) {
                        LoginRegisterScreen(navController = navController) }

                    composable(route = Screens.LoginScreen.route) {

                        val viewModel: LoginViewModel = hiltViewModel()
                        val state = viewModel.state.value

                        LaunchedEffect(key1 = Unit) {
                            if(googleAuthUiClient.getSignedInUser() != null) {
                                navController.navigate("profile")
                            }
                        }

                        val launcher = rememberLauncherForActivityResult(
                            contract = ActivityResultContracts.StartIntentSenderForResult(),
                            onResult = { result ->
                                if(result.resultCode == RESULT_OK) {
                                    lifecycleScope.launch {
                                        val signInResult = googleAuthUiClient.signInWithIntent(
                                            intent = result.data ?: return@launch
                                        )
                                        viewModel.onSignInResult(signInResult)
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

                                navController.navigate("profile")
                                viewModel.resetState()
                            }
                        }

                        LoginScreen(navController = navController,
                            state = state,
                            onSignInClick = {
                                lifecycleScope.launch {
                                    val signInIntentSender = googleAuthUiClient.signIn()
                                    launcher.launch(
                                        IntentSenderRequest.Builder(
                                            signInIntentSender ?: return@launch
                                        ).build()
                                    )
                                }
                            }
                        ) }

                    composable(route = Screens.RegisterScreen.route) {

                        val registerViewModel: RegisterViewmodel = hiltViewModel()

                        RegisterScreen(state = RegisterStates(),
                            navController = navController,
                            onEvent = registerViewModel::onEvent
                        ) }
                }
            }
        }
    }
}