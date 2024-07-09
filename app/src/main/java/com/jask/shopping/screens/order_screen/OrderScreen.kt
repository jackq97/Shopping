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
import androidx.compose.foundation.lazy.items
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
import com.jask.shopping.data.model.Order

@Composable
fun OrderScreen(
    states: OrderStates
){

    Surface(modifier = Modifier.fillMaxWidth()) {

        if (!states.isLoading) {
            Column(
                modifier = Modifier
                    .padding(horizontal = 16.dp)
            ) {
                LazyColumn {
                    items(states.order) { data ->
                        OrderStatusColumn(data = data)
                    }
                }
            }
        }
    }
}

@Composable
fun OrderStatusColumn(data: Order) {

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
                text = data.orderId.toString())

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
            text = data.date,
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
    OrderScreen(states = OrderStates())
    //OrderStatusColumn()
}