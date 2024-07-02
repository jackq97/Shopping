package com.jask.shopping.screens.cart_screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun CartScreen(){

    Surface(modifier = Modifier.fillMaxSize()) {

        Column(
            modifier = Modifier
                .padding(16.dp),
            verticalArrangement = Arrangement.Bottom,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            LazyColumn(modifier = Modifier.height(600.dp)) {
                items(10){
                    CartItemsColumn()
                }
            }

            Spacer(modifier = Modifier
                .weight(1f)
            )

            Row(modifier = Modifier
                .height(100.dp)
                .fillMaxWidth()
                .padding(horizontal = 30.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {

                TotalTitleText(label = "Total")

                Spacer(modifier = Modifier.weight(1f))

                TotalTitleText(label = "$150")
            }


            Button(
                modifier = Modifier
                    .width(400.dp)
                    .height(50.dp),
                shape = RoundedCornerShape(10),
                onClick = { /*TODO*/ }) {

                Text(text = "Check out",
                    fontSize = 20.sp
                )
            }
        }
    }
}

@Composable
fun TotalTitleText(label: String){

    Text(
        text = label,
        fontWeight = FontWeight.SemiBold
    )

}

@Composable
fun CartItemsColumn(){

    Row(modifier = Modifier
        .height(100.dp)
        .fillMaxWidth()
    ) {

        Image(
            modifier = Modifier
                .weight(1f)
                .fillMaxSize(),
            imageVector = Icons.Default.Add,
            contentDescription = "")

        Row(modifier = Modifier.weight(3f)
            .background(color = Color(0xFFF4F6F8))
        ) {

            Column(modifier = Modifier
                .weight(1f)
            ) {

                Spacer(modifier = Modifier.height(14.dp))

                Text(text = "Product Name",
                    fontWeight = FontWeight.Bold
                )

                Spacer(modifier = Modifier.height(8.dp))

                Text(text = "$25",
                    fontSize = 12.sp,
                    style = MaterialTheme.typography.labelSmall,
                    color = Color.Gray
                )

                Spacer(modifier = Modifier.height(8.dp))

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                ) {

                    Box(modifier = Modifier
                        .size(24.dp)
                        .clip(CircleShape)
                        .background(color = Color.Red)
                    )

                    Spacer(modifier = Modifier.width(4.dp))

                    Box(modifier = Modifier
                        .size(24.dp)
                        .clip(CircleShape)
                        .background(color = Color.Gray)
                    ){
                        Text(
                            modifier = Modifier
                                .align(Alignment.Center),
                            text = "L"
                        )
                    }
                }
            }

            Column(
                modifier = Modifier
                .weight(1f),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {

                Row {
                    Spacer(modifier = Modifier.weight(1f))
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        CartIcon()
                        Text(text = "1")
                        CartIcon()
                    }
                }
            }
        }
    }
}

@Composable
fun CartIcon(){

    IconButton(onClick = { /*TODO*/ }) {

        Box(
            modifier = Modifier
                .clip(shape = RoundedCornerShape(2.dp))
                .background(color = Color.Black)
                .size(16.dp)
        ) {
            Icon(
                modifier = Modifier,
                imageVector = Icons.Default.Add,
                contentDescription = "",
                tint = Color.White
            )
        }
    }
}

@Composable
@Preview
fun CartScreenPreview(){
    CartScreen()
    //CartItemsColumn()
}