package com.jask.shopping.screens.home_feed.composables

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.jask.shopping.R
import com.jask.shopping.data.model.CartProduct
import com.jask.shopping.data.model.Product
import com.jask.shopping.screens.home_feed.HomeFeedEvents
import com.jask.shopping.screens.home_feed.HomeFeedStates

@Composable
fun SpecialItemView(){

    Surface(modifier = Modifier
        .fillMaxSize()
        .background(MaterialTheme.colorScheme.background)
    ) {

        Column {
            TopProductView(specialProduct = Product(),
                onClick = { },
                onEvent = { },
                state = HomeFeedStates()
            )

            ProductView(imageUrl = "Megan",
                title = "Ligia",
                price = "Leopoldo",
                id = "",
                onClick = {})

            BestDealsView(imageUrl = "",
                title = "",
                price = "",
                id = "",
                onClick = {})
        }
    }
}

@Composable
fun TopProductView(
    specialProduct: Product,
    onClick: (String)-> Unit,
    onEvent: (HomeFeedEvents) -> Unit,
    state: HomeFeedStates
){

    Card(modifier = Modifier
        .width(270.dp)
        .height(180.dp)
        .padding(horizontal = 8.dp)
        .clickable {
            onClick(specialProduct.id)
        }) {

        Row {

            AsyncImage(
                modifier = Modifier
                    .weight(1.5f)
                    .fillMaxSize(),
                model = specialProduct.images[0],
                contentScale = ContentScale.Crop,
                contentDescription = null,
            )

            Column(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxSize()
                    .padding(start = 8.dp)
            ) {
                IconButton(
                    modifier = Modifier.align(Alignment.End),
                    onClick = {}) {
                    Icon(painterResource(id = R.drawable.favorite), contentDescription = "")
                }

                Text(
                    maxLines = 2,
                    text = specialProduct.name,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold
                )

                Text(
                    modifier = Modifier.padding(top = 10.dp),
                    text = specialProduct.price.toString(),
                    style = MaterialTheme.typography.labelLarge,
                )

                Box(modifier = Modifier.fillMaxSize()) {

                    Button(
                        modifier = Modifier
                            .padding(bottom = 2.dp, end = 5.dp)
                            .align(Alignment.BottomEnd)
                            .fillMaxWidth(),
                        contentPadding = PaddingValues(0.dp),
                        onClick = {
                            onEvent(
                            HomeFeedEvents.AddUpdateProduct(
                                cartProduct = CartProduct(
                                    product = specialProduct,
                                    quantity = 1,
                                    selectedColor = specialProduct.colors.first(),
                                    selectedSize = specialProduct.sizes.first(),
                                )
                            ))
                        }){

                        if (state.cartProducts.any { it.product.id == specialProduct.id }){

                            Icon(
                                modifier = Modifier.padding(end = 5.dp),
                                imageVector = Icons.Default.Check,
                                contentDescription = null
                            )
                        } else {

                            Text(
                                maxLines = 1,
                                text = "Add to Cart"
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun ProductView(
    imageUrl: String,
    title: String,
    price: String,
    id: String,
    onClick:(String) -> Unit
){

    Row(modifier = Modifier
        .width(350.dp)
        .height(100.dp)
        .padding(horizontal = 8.dp)) {

        AsyncImage(
            modifier = Modifier
                .weight(0.8f)
                .fillMaxSize()
                .clip(RoundedCornerShape(10.dp)),
            model = imageUrl,
            contentDescription = null)

        Column(modifier = Modifier
            .fillMaxSize()
            .weight(1f)
            .padding(
                top = 18.dp,
                start = 10.dp
            ),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.Start
        ) {

            Text(text = title,
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold)

            Row {

                Text(text = price,
                    style = MaterialTheme.typography.labelMedium
                )

                Text(
                    modifier = Modifier.padding(start = 10.dp),
                    color = Color.Gray,
                    style = MaterialTheme.typography.labelMedium,
                    text = "$2000")
            }
        }

        Box(modifier = Modifier
            .weight(1f)
            .fillMaxSize()){
            Button(
                modifier = Modifier.align(Alignment.Center),
                shape = RoundedCornerShape(5.dp),
                contentPadding = PaddingValues(horizontal = 8.dp),
                onClick = { onClick(id) }) {
                Text(text = "See product",
                    style = MaterialTheme.typography.labelLarge
                )
            }
        }
    }
}

@Composable
fun BestDealsView(
    imageUrl: String,
    title: String,
    price: String,
    id: String,
    onClick:(String) -> Unit
){
    Card(modifier = Modifier
        .width(150.dp)
        .height(170.dp)
        .padding(
            horizontal = 8.dp,
            vertical = 4.dp
        )
        .clickable {
            onClick(id)
        }
    ) {

        Column {

            AsyncImage(
                modifier = Modifier
                    .weight(7f)
                    .fillMaxSize(),
                model = imageUrl,
                contentScale = ContentScale.Crop,
                contentDescription = "")

            Row(modifier = Modifier
                .weight(1f)
                .fillMaxSize()) {

                Text(
                    modifier = Modifier
                        .weight(7f)
                        .padding(start = 10.dp),
                    text = title)

                IconButton(
                    modifier = Modifier.weight(3f),
                    onClick = { /*TODO*/ }) {
                    Icon(painterResource(id = R.drawable.favorite), contentDescription = "")
                }
            }

            Row(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxSize(),
                horizontalArrangement = Arrangement.Start,
                verticalAlignment = Alignment.CenterVertically
            ) {

                Text(
                    modifier = Modifier.padding(start = 10.dp),
                    text = price,
                    style = MaterialTheme.typography.labelMedium
                )

                Text(
                    modifier = Modifier.padding(start = 10.dp),
                    color = Color.Gray,
                    style = MaterialTheme.typography.labelMedium,
                    text = "$2000")
            }
        }
    }
}

@Preview
@Composable
fun SpecialItemViewPreview() {
    SpecialItemView()
}