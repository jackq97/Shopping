package com.jask.shopping.screens.order_screen

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.jask.shopping.data.model.Order
import com.jask.shopping.data.model.OrderStatus
import com.jask.shopping.data.model.getOrderStatus

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OrderScreen(
    states: OrderStates,
    navController: NavController
){

    Surface(modifier = Modifier.fillMaxWidth()) {

        if (!states.isLoading) {
            Column(
                modifier = Modifier
                    .padding(horizontal = 16.dp)
            ) {

                TopAppBar(title = { Text(text = "Orders") })

                LazyColumn {
                    items(states.order) { data ->
                        OrderStatusColumn(data = data,
                            navController = navController)
                    }
                }
            }
        }
    }
}

@Composable
fun OrderStatusColumn(data: Order,
                      navController: NavController
) {

    val color = when (getOrderStatus(data.orderStatus)) {
        is OrderStatus.Ordered -> Color(0xFFFF9800)
        is OrderStatus.Canceled -> Color(0xFFF44336)
        is OrderStatus.Confirmed -> Color(0xFF4CAF50)
        is OrderStatus.Shipped -> Color(0xFF4CAF50)
        is OrderStatus.Delivered -> Color(0xFF4CAF50)
        is OrderStatus.Returned -> Color(0xFFF44336)
    }

    Column(modifier = Modifier
        .clickable {
            navController.navigate("order_details_screen/${data.orderId}")
        }
    ) {

        Row(
            modifier = Modifier.padding(top = 10.dp),
            verticalAlignment = Alignment.CenterVertically) {
            Box(
                modifier = Modifier
                    .size(10.dp)
                    .clip(shape = CircleShape)
                    .background(color = color)
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
    OrderScreen(states = OrderStates(),
        navController = rememberNavController()
        )
    //OrderStatusColumn()
}