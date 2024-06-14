package com.jask.shopping.screens.profile_screen

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun ProfileScreen(
    onEvent: (ProfileEvents) -> Unit
){

    Surface {
        Column {
            Button(onClick = {
                onEvent(ProfileEvents.SignOut)
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
        onEvent = {}
    )
}