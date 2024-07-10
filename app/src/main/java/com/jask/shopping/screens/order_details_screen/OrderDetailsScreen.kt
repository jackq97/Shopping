package com.jask.shopping.screens.order_details_screen

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.jask.shopping.data.model.Order
import com.jask.shopping.screens.billing_screen.CartContentRow
import com.jask.shopping.screens.cart_screen.TotalCartInfoRow

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OrderDetailsScreen(
    states: OrderDetailsStates,
    id: String,
    onEvent: (OrderEvents) -> Unit
    ) {

    LaunchedEffect(key1 = Unit) {
        onEvent(
            OrderEvents.GetOrderById(id = id)
        )
    }

    Surface(modifier = Modifier.fillMaxSize()) {

        if (states.order != null) {
            Column(
                modifier = Modifier
                    .padding(horizontal = 16.dp)
            ) {

                TopAppBar(title = { Text(text = "Order Details") })

                OrderInfoRow()

                Column {

                    Spacer(modifier = Modifier.height(30.dp))

                    HorizontalDivider(
                        modifier = Modifier.fillMaxWidth(),
                        color = Color.Gray
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(imageVector = Icons.Default.LocationOn, contentDescription = null)
                        Text(
                            text = states.order.address.addressTitle,
                            fontWeight = FontWeight.Bold,
                        )
                    }
                    Text(
                        text = states.order.address.street,
                        color = Color.Gray
                    )
                    Text(
                        text = states.order.address.city,
                        color = Color.Gray
                    )
                    Text(
                        text = states.order.address.state,
                        color = Color.Gray
                    )
                }

                Spacer(modifier = Modifier.height(10.dp))

                HorizontalDivider(
                    modifier = Modifier.fillMaxWidth(),
                    color = Color.Gray
                )

                Spacer(modifier = Modifier.height(20.dp))

                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(imageVector = Icons.Default.ShoppingCart, contentDescription = null)
                    Text(
                        text = "Products",
                        fontWeight = FontWeight.Bold
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))

                LazyColumn(modifier = Modifier.height(400.dp)
                    .fillMaxWidth()
                ) {
                    items(states.order.products) { data ->
                        CartContentRow(
                            width = 400.dp,
                            data = data)
                    }
                }

                Spacer(modifier = Modifier.weight(1f))

                TotalCartInfoRow(cartProduct = states.order.products)
            }
        }
    }
}

@Composable
fun OrderInfoRow(){

    Column {
        Row(modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            StatusInfoColumn(modifier = Modifier.weight(1f),
                steps = "1",
                color = Color(0xFFFFEB3B)
            )
            StatusDivider(modifier = Modifier
                .weight(1f))
            StatusInfoColumn(modifier = Modifier.weight(1f),
                steps = "2",
                color = Color.Transparent //Color(0xFFFEB800)
            )
            StatusDivider(modifier = Modifier
                .weight(1f))
            StatusInfoColumn(modifier = Modifier.weight(1f),
                steps = "3",
                color = Color.Transparent //Color(0xFF4CAF50)
            )
            StatusDivider(modifier = Modifier
                .weight(1f))
            StatusInfoColumn(modifier = Modifier.weight(1f),
                steps = "4",
                color = Color.Transparent //Color(0xFF4CAF50)
            )
        }

        Spacer(modifier = Modifier.height(10.dp))

        Row {
            DeliverText(
                modifier = Modifier.weight(1f),
                label = "Ordered")
            DeliverText(
                modifier = Modifier
                    .weight(1f)
                    .padding(start = 7.dp),
                label = "Confirmed")
            DeliverText(
                modifier = Modifier
                    .weight(1f)
                    .padding(start = 25.dp),
                label = "Shipped")
            DeliverText(
                modifier = Modifier
                    .weight(1f)
                    .padding(start = 30.dp),
                label = "Delivered")
        }
    }

}

@Composable
fun DeliverText(
    modifier: Modifier,
    label: String){
    Text(
        modifier = modifier
            .fillMaxWidth(),
        text = label,
        color = Color.Gray
    )
}

@Composable
fun StatusDivider(modifier: Modifier){
    HorizontalDivider(modifier = modifier,
        thickness = 3.dp,
        color = Color(0xFFA2A2A2)
    )
}

@Composable
fun StatusInfoColumn(modifier: Modifier,
                     steps: String,
                     color: Color){

    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally) {
        Box(modifier = Modifier
            .size(30.dp)
            .clip(shape = CircleShape)
            .background(color = color)
        ) {
            Text(
                modifier = Modifier
                    .align(Alignment.Center),
                text = steps,
                color = Color.Gray,
            )
        }

    }
}

@Composable
@Preview(showBackground = true)
fun OrderDetailsScreenPreview(){
    OrderDetailsScreen(
        states = OrderDetailsStates(
            isLoading = false,
            isSuccess = false,
            order = Order(),
            isError = null
        ),
        id = "",
        onEvent = {}
    )
    //OrderInfoRow()
}