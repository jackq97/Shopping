package com.jask.shopping.screens.order_screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun OrderScreen(){
    Surface(modifier = Modifier.fillMaxWidth()) {
        Column(modifier = Modifier
            .padding(horizontal = 16.dp)) {
            LazyColumn {
                items(10) {
                    OrderStatusColumn()
                }
            }
        }
    }
}

@Composable
fun OrderStatusColumn(){

    Column {

        Row(
            modifier = Modifier.padding(top = 10.dp),
            verticalAlignment = Alignment.CenterVertically) {
            Box(
                modifier = Modifier
                    .size(10.dp)
                    .clip(shape = CircleShape)
                    .background(color = Color.Red)
            )

            Text(
                modifier = Modifier.padding(start = 3.dp),
                text = "98765434567")

            Spacer(modifier = Modifier.weight(1f))

            IconButton(
                modifier = Modifier.size(20.dp),
                onClick = { /*TODO*/ }) {
                Icon(
                    imageVector = Icons.Filled.KeyboardArrowRight,
                    contentDescription = null
                )
            }
        }

        Text(
            modifier = Modifier.padding(bottom = 10.dp),
            text = "2023-02-10",
            color = Color.Gray
        )

        HorizontalDivider(modifier = Modifier.fillMaxWidth())
    }
}

@Composable
@Preview(
    showBackground = true
)
fun OrderScreenPreview(){
    OrderScreen()
    //OrderStatusColumn()
}