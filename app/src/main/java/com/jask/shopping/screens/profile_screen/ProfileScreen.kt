package com.jask.shopping.screens.profile_screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.jask.shopping.R
import com.jask.shopping.navigation.Screens

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreen(
    onEvent: (ProfileEvents) -> Unit,
    navController: NavController,
    mainNavController: NavController
){

    Surface(modifier = Modifier
        .fillMaxSize()
        .background(color = Color(0xFFF2F2F2))){

        Column(modifier = Modifier
            .padding(horizontal = 16.dp)
        ) {

            TopAppBar(title = { Text(text = "Settings") })

            Row(verticalAlignment = Alignment.CenterVertically) {
                Image(
                    modifier = Modifier
                        .size(50.dp)
                        .clip(shape = CircleShape)
                        .background(Color.Gray),
                    imageVector = Icons.Default.Person,
                    contentDescription = null
                )
                Column(modifier = Modifier
                    .padding(start = 10.dp)
                ) {
                    Text(text = "Jaskaran singh",
                        fontWeight = FontWeight.Bold,
                        fontSize = 20.sp
                    )
                    Text(text = "edit personal details",
                        color = Color.Gray,
                        fontSize = 10.sp
                    )
                }

                Spacer(modifier = Modifier.weight(1f))

                Icon(
                    modifier = Modifier.size(30.dp),
                    imageVector = Icons.Default.KeyboardArrowRight,
                    contentDescription = null,
                    tint = Color.Gray
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            TitleText(label = "Orders")

            Spacer(modifier = Modifier.height(10.dp))

            SettingSelectorRow(
                icon = R.drawable.shopping_cart,
                label = "All Orders",
                color = Color(0xFF036BEC),
                onClicked = {
                    navController.navigate(Screens.OrderScreen.route)
                }
            )
            SettingSelectorRow(
                icon = R.drawable.search,
                label = "Track Orders",
                color = Color(0xFF646464),
                onClicked = {

                }
            )
            SettingSelectorRow(
                icon = R.drawable.wallet,
                label = "Billing",
                color = Color(0xFFF39B17),
                onClicked = {
                    navController.navigate(Screens.BillingScreen.route)
                }
            )

            Spacer(modifier = Modifier.height(18.dp))

            TitleText(label = "Regional")

            Spacer(modifier = Modifier.height(8.dp))

            SettingSelectorRow(
                icon = R.drawable.logout,
                label = "Logout",
                color = Color(0xFFF98D51),
                onClicked = {
                    onEvent(ProfileEvents.SignOut)
                    mainNavController.navigate(Screens.LoginScreen.route)
                }
            )

            Spacer(modifier = Modifier.weight(1f))

            Text(
                modifier = Modifier
                    .padding(vertical = 20.dp)
                    .fillMaxWidth(),
                text = "Version 1",
                color = Color.Gray,
                textAlign = TextAlign.Center
            )
        }
    }
}

@Composable
fun TitleText(label: String){

    Text(text = label,
        color = Color.Gray,
        fontSize = 15.sp
    )
}

@Composable
fun SettingSelectorRow(
    icon: Int,
    label: String,
    color: Color,
    onClicked: () -> Unit){

    Row(
        modifier = Modifier
            .background(color = Color(0xFFF7FAFB))
            .padding(vertical = 10.dp)
            .clickable { onClicked() },
        verticalAlignment = Alignment.CenterVertically) {

        Box(modifier = Modifier
            .background(color = color)
            .clip(shape = CircleShape)
        ){

            Icon(
                modifier = Modifier
                    .size(30.dp)
                    .padding(3.dp),
                painter = painterResource(id = icon),
                contentDescription = null
            )
        }

        Spacer(modifier = Modifier.width(15.dp))

        Text(text = label)

        Spacer(modifier = Modifier.weight(1f))

        Icon(
            modifier = Modifier.size(30.dp),
            imageVector = Icons.Default.KeyboardArrowRight,
            contentDescription = null,
            tint = Color.Gray
        )
    }
}

@Composable
@Preview
fun ProfileScreenPreview(){

    ProfileScreen(
        onEvent = {},
        mainNavController = rememberNavController(),
        navController = rememberNavController()
    )
}