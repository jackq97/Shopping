package com.jask.shopping.screens.cart_screen

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
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import coil.compose.AsyncImage
import com.jask.shopping.data.model.CartProduct
import com.jask.shopping.navigation.Screens

@Composable
fun CartScreen(
    state: CartStates,
    onEvent: (CartEvents) -> Unit,
    navController: NavController
){

    Surface(modifier = Modifier.fillMaxSize()) {

        if (!state.isLoading) {
            Column(
                modifier = Modifier
                    .padding(16.dp),
                verticalArrangement = Arrangement.Bottom,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                LazyColumn(modifier = Modifier.height(600.dp)) {
                    items(state.cartProduct) { data ->
                        CartItemsColumn(data,
                            onEvent = onEvent)
                    }
                }

                Spacer(
                    modifier = Modifier
                        .weight(1f)
                )

                Row(
                    modifier = Modifier
                        .height(100.dp)
                        .fillMaxWidth()
                        .padding(horizontal = 30.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {

                    TotalTitleText(label = "Total")

                    Spacer(modifier = Modifier.weight(1f))

                    TotalTitleText(label = "$ ${state.cartProduct.sumOf { it.product.price.toInt() * it.quantity }}")
                }

                Button(
                    modifier = Modifier
                        .width(400.dp)
                        .height(50.dp),
                    shape = RoundedCornerShape(10),
                    onClick = {
                        navController.navigate(Screens.BillingScreen.route)
                    }) {

                    Text(
                        text = "Check out",
                        fontSize = 20.sp
                    )
                }
            }
        } else {
            Box(modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center) {
                CircularProgressIndicator()
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
fun CartItemsColumn(cartProduct: CartProduct,
                    onEvent: (CartEvents) -> Unit
                    ){

    Card(modifier = Modifier
        .fillMaxWidth()
        .height(150.dp)
        .padding(bottom = 8.dp)
    ) {

        Row {

        AsyncImage(
            modifier = Modifier.weight(1f),
            model = cartProduct.product.images.first(),
            contentScale = ContentScale.Crop,
            contentDescription = null
        )

        Row(modifier = Modifier
            .fillMaxSize()
            .weight(3f)
            .padding(start = 10.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {

            Column(
                modifier = Modifier
                    .weight(1f)
            ) {

                Spacer(modifier = Modifier.height(14.dp))

                Text(
                    text = cartProduct.product.name,
                    fontWeight = FontWeight.Bold
                )

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = cartProduct.product.price.toString(),
                    fontSize = 12.sp,
                    style = MaterialTheme.typography.labelSmall,
                    color = Color.Gray
                )

                Spacer(modifier = Modifier.height(8.dp))

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                ) {

                    Box(
                        modifier = Modifier
                            .size(24.dp)
                            .clip(CircleShape)
                            .background(color = Color.Red)
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

            Column(
                modifier = Modifier
                    .weight(1f),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {

                Row {
                    Spacer(modifier = Modifier.weight(1f))
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        // increase
                        CartIcon(
                            onclick = {
                                onEvent(
                                    CartEvents.IncreaseQuantity(
                                        documentId = cartProduct.product.id
                                    )
                                )
                            }
                        )

                        Text(text = cartProduct.quantity.toString())

                        // decrease
                        CartIcon(
                            onclick = {
                                onEvent(
                                    CartEvents.DecreaseQuantity(
                                        documentId = cartProduct.product.id
                                    )
                                )
                            }
                        )
                    }
                }
            }
        }
        }
    }
}

@Composable
fun CartIcon(
    onclick: () -> Unit
){

    IconButton(onClick = onclick) {

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
    CartScreen(
        state = CartStates(
            isLoading = false, isSuccess = false, cartProduct = listOf(), isError = null
        ),
        onEvent = {},
        navController = rememberNavController()
    )
    //CartItemsColumn()
}