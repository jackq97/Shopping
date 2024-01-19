package com.jask.shopping.navigation

import android.content.Context
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.IntentSenderRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.jask.shopping.presentation.screens.LoginRegisterScreen
import com.jask.shopping.presentation.screens.login_screen.GoogleAuthUiClient
import com.jask.shopping.presentation.screens.login_screen.LoginScreen
import com.jask.shopping.presentation.screens.login_screen.LoginViewModel
import com.jask.shopping.presentation.screens.register_screen.RegisterScreen
import com.jask.shopping.presentation.screens.register_screen.RegisterStates
import com.jask.shopping.presentation.screens.register_screen.RegisterViewmodel
import kotlinx.coroutines.launch

@Composable
fun NavigationGraph(
) {}