package com.jask.shopping.screens.product_view_screen

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.HorizontalPagerIndicator
import com.google.accompanist.pager.rememberPagerState
import com.jask.shopping.data.model.CartProduct
import com.jask.shopping.data.model.Product
import com.jask.shopping.util.hexStringToColor

@OptIn(ExperimentalPagerApi::class)
@Composable
fun ProductViewScreen(
    state: ProductViewStates,
    onEvent: (ProductViewEvents) -> Unit,
    id: String) {

    val context = LocalContext.current

    LaunchedEffect(key1 = Unit) {
        onEvent(
            ProductViewEvents.GetProductsByCategory(id = id)
        )
    }

    val pagerState = rememberPagerState()

    Surface(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {

        if (state.isSuccess && state.product != null) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(12.dp)
                ){

                val product = state.product

                val pages: List<String> = product.images

                var selectedSize = product.sizes.first()
                var selectedColor = product.colors.first()

                Card(
                    modifier = Modifier
                        .height(400.dp)
                        .fillMaxWidth()
                ) {

                    HorizontalPager(
                        count = pages.size,
                        state = pagerState,
                        modifier = Modifier
                            .fillMaxSize()
                            .background(MaterialTheme.colorScheme.background)
                    ) { page ->
                        Box(modifier = Modifier.align(Alignment.CenterHorizontally)) {
                            androidx.compose.material.Surface(
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                AsyncImage(
                                    model = pages[page],
                                    contentDescription = null,
                                    contentScale = ContentScale.Crop
                                )
                            }
                        }
                    }
                }

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(10.dp)
                ) {
                    HorizontalPagerIndicator(
                        modifier = Modifier.align(Alignment.Center),
                        pagerState = pagerState,
                        activeColor = MaterialTheme.colorScheme.onBackground,
                        inactiveColor = Color.Gray,
                        indicatorShape = RoundedCornerShape(size = 2.dp)
                    )
                }

                Row(
                    modifier = Modifier
                        .padding(top = 8.dp),
                    verticalAlignment = Alignment.Bottom,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {

                    Text(
                        text = product.name,
                        style = MaterialTheme.typography.headlineMedium
                    )

                    Spacer(modifier = Modifier.weight(1f))

                    Text(
                        text = "$ ${product.price.toInt()}",
                        style = MaterialTheme.typography.bodyMedium,
                        fontSize = 25.sp
                    )
                }

                HorizontalDivider(
                    modifier = Modifier.padding(top = 5.dp),
                    color = Color.Gray
                )

                Spacer(modifier = Modifier.height(10.dp))

                Text(
                    text = "Colors",
                    style = MaterialTheme.typography.titleSmall
                )

                ColorSelectRadioButton(onClickOption = { color ->
                    selectedColor = color
                }, product = product)

                Text(
                    text = "Sizes",
                    style = MaterialTheme.typography.titleSmall
                )

                SizeSelectRadioButton(onClickOption = { size ->
                    selectedSize = size
                }, product = product)

                Text(text = "Description",
                    style = MaterialTheme.typography.titleSmall
                )

                Spacer(modifier = Modifier.height(2.dp))

                HorizontalDivider()

                Spacer(modifier = Modifier.weight(1f))

                Button(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(4.dp),
                    onClick = {
                        onEvent(
                            ProductViewEvents.AddUpdateProduct(
                                CartProduct(
                                    product = product,
                                    quantity = 1,
                                    selectedColor = selectedColor,
                                    selectedSize = selectedSize
                                )
                            )
                        )
                    }) {


                    if (state.isAddProductLoading) {
                        CircularProgressIndicator(
                            color = Color.White,
                            modifier = Modifier.size(ButtonDefaults.IconSize),
                            strokeWidth = 2.dp
                        )
                    } else {
                        if (state.isAddProductSuccess) {
                            Toast.makeText(context, "Product Added", Toast.LENGTH_SHORT).show()
                            state.isAddProductSuccess = false
                        }
                        Text(
                            text = "Add To Card",
                            style = MaterialTheme.typography.labelMedium
                        )
                    }

                }
            }
        } else {
            Box(modifier = Modifier.fillMaxSize()){
                CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
            }
        }
    }
}

@Composable
fun ColorSelectRadioButton(
    onClickOption: (String) -> Unit,
    product: Product) {

    val options: List<String> = product.colors

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

        options.forEach { color ->

            val bgColor: Color = hexStringToColor(color)

            Box(modifier = Modifier
                .size(30.dp)
                .padding(end = 8.dp)
                .border(width = 1.dp, color = Color.Gray, shape = CircleShape)
                .clip(shape = RoundedCornerShape(100))
                .background(color = bgColor)
                .clickable {
                    onSelectionChange(
                        color
                    )
                    onClickOption(
                        color
                    )
                },
                contentAlignment = Alignment.Center
            ){
                if (color == selectedOption) {
                    Icon(
                        imageVector = Icons.Default.Check,
                        contentDescription = null,
                        tint = Color(0xFF0013B3)
                    )
                }
            }
        }
    }
}

@Composable
fun SizeSelectRadioButton(
    onClickOption: (String) -> Unit,
    product: Product) {

    val options: List<String> = product.sizes

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

            Box(modifier = Modifier
                .size(30.dp)
                .padding(end = 8.dp)
                .clip(shape = RoundedCornerShape(100))
                .background(color = Color.Gray)
                .clickable {
                    onSelectionChange(
                        text
                    )
                    onClickOption(
                        text
                    )
                },
                contentAlignment = Alignment.Center
            ){

                Text(
                    text = text.uppercase(),
                    color = if (text == selectedOption) {
                        Color(0xFF0013B3)

                    } else {
                        Color.Black
                    }
                )
            }
        }
    }

}

@Composable
@Preview
fun ProductViewPreview(){
    ProductViewScreen(
        state = ProductViewStates(),
        onEvent = {},
        id = ""
    )
}