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
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import coil.compose.AsyncImage
import com.jask.shopping.R
import com.jask.shopping.data.model.CartProduct
import com.jask.shopping.data.model.Product
import com.jask.shopping.navigation.Screens
import com.jask.shopping.util.hexStringToColor

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CartScreen(
    state: CartStates,
    onEvent: (CartEvents) -> Unit,
    navController: NavController
){

    val openAlertDialog = remember { mutableStateOf(false) }

    LaunchedEffect(key1 = Unit) {
        onEvent(
            CartEvents.GetCardProducts
        )
    }

    when {
        openAlertDialog.value -> {
            AlertDialogExample(
                onDismissRequest = { openAlertDialog.value = false },
                onConfirmation = {
                    openAlertDialog.value = false

                    navController.navigate(Screens.BillingScreen.route)
                },
                dialogTitle = "Place order?",
                dialogText = "confirming will place your order.",
                icon = Icons.Default.Info
            )
        }
    }

    Surface(modifier = Modifier.fillMaxSize()) {

        if (state.cartProduct != null) {
            Column(
                modifier = Modifier
                    .padding(16.dp),
                verticalArrangement = Arrangement.Bottom,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                TopAppBar(title = { Text(text = "Cart") })

                LazyColumn(modifier = Modifier.weight(1f)) {
                    items(state.cartProduct) { data ->
                        CartItemsColumn(data,
                            onEvent = onEvent
                        )
                    }
                }

                TotalCartInfoRow(cartProduct = state.cartProduct)

                Button(
                    modifier = Modifier
                        .width(400.dp)
                        .height(50.dp),
                    shape = RoundedCornerShape(10),
                    onClick = {
                        openAlertDialog.value = true
                    }) {

                    Text(
                        text = "Check out",
                        fontSize = 20.sp
                    )
                }
            }
        }
    }
}

@Composable
fun AlertDialogExample(
    onDismissRequest: () -> Unit,
    onConfirmation: () -> Unit,
    dialogTitle: String,
    dialogText: String,
    icon: ImageVector,
) {
    AlertDialog(
        icon = {
            Icon(icon, contentDescription = "Example Icon")
        },
        title = {
            Text(text = dialogTitle)
        },
        text = {
            Text(text = dialogText)
        },
        onDismissRequest = {
            onDismissRequest()
        },
        confirmButton = {
            TextButton(
                onClick = {
                    onConfirmation()
                }
            ) {
                Text("Confirm")
            }
        },
        dismissButton = {
            TextButton(
                onClick = {
                    onDismissRequest()
                }
            ) {
                Text("Dismiss")
            }
        }
    )
}

@Composable
fun TotalCartInfoRow(cartProduct: List<CartProduct>){
    Row(
        modifier = Modifier
            .height(80.dp)
            .fillMaxWidth()
            .padding(horizontal = 30.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {

        TotalTitleText(label = "Total")

        Spacer(modifier = Modifier.weight(1f))

        TotalTitleText(label = "$ ${cartProduct.sumOf { it.product.price.toInt() * it.quantity }}")
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
fun CartItemsColumn(
    cartProduct: CartProduct,
    onEvent: (CartEvents) -> Unit){

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
        contentDescription = null)

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
                            .background(color = hexStringToColor(cartProduct.selectedColor!!))
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
                            text = cartProduct.selectedSize!!
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
                            icon = R.drawable.add,
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
                            icon = R.drawable.remove,
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
    icon: Int,
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
                painter = painterResource(id = icon),
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
            isLoading = false,
            isSuccess = true,
            cartProduct = listOf(
                CartProduct(
                product = Product(
                    id = "Test",
                    name = "Test",
                    category = "Test",
                    dealType = "Test",
                    price = 0f,
                    offerPercentage = 0f,
                    description = "Test",
                    images = listOf(""),
                    colors = listOf("ff7e673c"),
                    sizes = listOf("L")
                ),
                quantity = 1,
                selectedColor = "ff7e673c",
                selectedSize = "L"
                )
            ),
            isError = null
        ),
        onEvent = {},
        navController = rememberNavController()
    )
}