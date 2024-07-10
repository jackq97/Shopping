package com.jask.shopping.screens.billing_screen

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
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import coil.compose.AsyncImage
import com.jask.shopping.data.model.Address
import com.jask.shopping.data.model.CartProduct
import com.jask.shopping.navigation.Screens

@Composable
fun BillingScreen(
    navController: NavController,
    states: BillingStates,
    onEvent: (BillingEvents) -> Unit
){

    var selectedAddress: Address = states.addressList.first()

    Surface(modifier = Modifier.fillMaxSize()) {

        Column(modifier = Modifier
            .padding(horizontal = 16.dp)) {

            if (!states.isLoading) {
                Text(
                    modifier = Modifier.padding(vertical = 20.dp),
                    text = "Payment Methods",
                    fontWeight = FontWeight.Bold
                )

                Text(
                    text = "We do not support virtual payments. You can pay when you receive your order.",
                    style = MaterialTheme.typography.titleSmall
                )

                HorizontalDivider(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 10.dp)
                )

                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(imageVector = Icons.Default.LocationOn, contentDescription = null)
                    Text(
                        text = "Shipping Address",
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(modifier = Modifier.weight(1f))
                    IconButton(onClick = {
                        navController.navigate(Screens.AddressScreen.route)
                    }) {
                        Icon(imageVector = Icons.Default.Add, contentDescription = null)
                    }
                }

                Spacer(modifier = Modifier.height(6.dp))

                LineRadioButtons(
                    onClickOption = { addressTitle ->
                        selectedAddress = states.addressList.first {
                            it.addressTitle == addressTitle
                        }
                    },
                    states = states
                )

                Spacer(modifier = Modifier.height(10.dp))

                HorizontalDivider(modifier = Modifier.fillMaxWidth())

                LazyRow(modifier = Modifier.padding(vertical = 16.dp)) {
                    items(states.cartProduct) { data ->
                        CartContentRow(data = data)
                    }
                }

                HorizontalDivider(modifier = Modifier.fillMaxWidth())

                val stroke = Stroke(
                    width = 2f,
                    pathEffect = PathEffect.dashPathEffect(floatArrayOf(10f, 10f), 0f)
                )

                Row(
                    modifier = Modifier
                        .height(100.dp)
                        .padding(vertical = 20.dp)
                        .drawBehind {
                            drawRoundRect(color = Color.Gray, style = stroke)
                        },
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        modifier = Modifier
                            .padding(start = 10.dp),
                        text = "Total",
                        fontWeight = FontWeight.Bold,
                        fontSize = 20.sp
                    )

                    Spacer(modifier = Modifier.weight(1f))

                    Text(
                        modifier = Modifier
                            .padding(end = 10.dp),
                        text = "$ ${states.cartProduct.sumOf { it.product.price.toInt() * it.quantity }}",
                        fontWeight = FontWeight.Bold,
                        fontSize = 20.sp
                    )
                }

                Button(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFF0013B3)
                    ),
                    shape = RoundedCornerShape(20),
                    onClick = {
                        /*onEvent(
                            BillingEvents.PlaceOrder(
                                order = Order(
                                    orderStatus = OrderStatus.Ordered.status,
                                    totalPrice = (states.cartProduct.sumOf { it.product.price.toInt() * it.quantity}.toFloat()),
                                    products = states.cartProduct,
                                    address = selectedAddress,
                                )
                            )
                        )*/
                        navController.navigate(Screens.OrderScreen.route)
                    }) {
                    Text(
                        text = "Place Order",
                        fontSize = 18.sp
                    )
                }
            } else {
                CircularProgressIndicator()
            }
        }
    }
}

@Composable
fun LineRadioButtons(onClickOption: (String) -> Unit,
                     states: BillingStates) {

    val options = states.addressList.map { it.addressTitle }

    var selectedOption by remember {
        mutableStateOf(options.first().toString())
    }

    val onSelectionChange = { text: String ->
        selectedOption = text
    }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(40.dp),
        horizontalArrangement = Arrangement.Start,
        verticalAlignment = Alignment.CenterVertically
    ) {

        options.forEach { text ->

            val color = if (text == selectedOption) Color(0xFF0013B3) else Color(0xFFF4F6F8)

            Button(
                onClick = {
                    onSelectionChange(text)
                    onClickOption(text)
                },
                modifier = Modifier
                    .width(100.dp)
                    .padding(end = 8.dp)
                ,
                shape = RectangleShape,
                colors = ButtonDefaults.buttonColors(
                    containerColor = color
                )
            ) {
                Text(
                    text = text,
                    fontWeight = FontWeight.Bold,
                    color = Color.Gray
                )
            }

        }
    }
}

@Composable 
fun CartContentRow(
    width: Dp = 250.dp,
    data: CartProduct) {

    Card(
        modifier = Modifier
            .height(100.dp)
            .width(width)
            .padding(end = 8.dp,
                bottom = 8.dp
            ),
        shape = RectangleShape,
        colors = CardDefaults.cardColors(
            containerColor = Color(0xFFF5F6F8)
        )
    ) {

        Row(modifier = Modifier.fillMaxSize(),
            verticalAlignment = Alignment.CenterVertically
            ) {

            AsyncImage(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxSize(),
                model = data.product.images.first(),
                contentDescription = null,
                contentScale = ContentScale.Crop
            )

            Row(modifier = Modifier
                .weight(1.5f)
                .fillMaxSize()
                .padding(start = 4.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {

                Column {

                    Text(text = data.product.name,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        fontWeight = FontWeight.SemiBold
                    )

                    Text(
                        modifier = Modifier.padding(vertical = 6.dp),
                        text = "$ ${data.product.price}")

                    Row {

                        Box(
                            modifier = Modifier
                                .size(24.dp)
                                .clip(CircleShape)
                                .background(color = Color.Black)
                        )

                        Spacer(modifier = Modifier.width(4.dp))

                        Box(
                            modifier = Modifier
                                .size(24.dp)
                                .clip(CircleShape)
                                .background(color = Color.Gray)
                        ) {
                            Text(
                                modifier = Modifier
                                    .align(Alignment.Center),
                                text = "L"
                            )
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.weight(1f))

            Text(
                modifier = Modifier.padding(3.dp),
                text = data.quantity.toString(),
                color = Color.Gray
            )
        }
    }
}

@Composable
@Preview
fun BillingScreenPreview(){
    BillingScreen(navController = rememberNavController(),
        states = BillingStates(
            isLoading = false,
            isSuccess = false,
            cartProduct = listOf(),
            addressList = listOf(),
            addressTitle = listOf(),
            isError = null
        ),
        onEvent = {}
    )
    //CartContentRow()
}