package com.jask.shopping.screens.profile_screen

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.jask.shopping.navigation.Screens

@Composable
fun ProfileScreen(
    onEvent: (ProfileEvents) -> Unit,
    mainNavController: NavController
){

    Surface {
        Column {
            Button(onClick = {
                onEvent(ProfileEvents.SignOut)
                mainNavController.navigate(Screens.LoginRegisterScreen.route)
            }) {
                Text(text = "Sign out")
            }
        }
    }
}

@Composable
@Preview
fun ProfileScreenPreview(){

    ProfileScreen(
        onEvent = {},
        mainNavController = rememberNavController()
    )
}