package com.jask.shopping.presentation.screens.product_view_screen

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
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.HorizontalPagerIndicator
import com.google.accompanist.pager.rememberPagerState

@OptIn(ExperimentalPagerApi::class)
@Composable
fun ProductViewScreen(
    index: String,
    state: ProductViewStates,
    onEvent: (ProductViewStates) -> Unit,
) {

    val pagerState = rememberPagerState()

    val product = state.specialProduct

    val listOfColors = listOf(Color.Black, Color.Red, Color.Gray, Color.Blue, Color.Magenta, Color.Yellow)
    val listOfSizes = listOf("XL", "S", "M", "L")

    val pages: List<String> = product[index.toInt()].images

    Surface(
        modifier = Modifier.fillMaxSize()) {

        Column(modifier = Modifier
            .background(Color.White)
            .fillMaxSize()
            .padding(12.dp)) {

            Card(modifier = Modifier
                .height(400.dp)
                .fillMaxWidth()) {
                HorizontalPager(
                    count = pages.size,
                    state = pagerState,
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Color.LightGray)
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

            Box(modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp)) {
                HorizontalPagerIndicator(
                    modifier = Modifier.align(Alignment.Center),
                    pagerState = pagerState,
                    activeColor = Color.Black,
                    inactiveColor = Color.Gray,
                    indicatorShape = RoundedCornerShape(size = 2.dp)
                )
            }

            Row(modifier = Modifier
                .padding(top = 8.dp),
                verticalAlignment = Alignment.Bottom,
                horizontalArrangement = Arrangement.SpaceBetween
                ) {

                Text(
                    text = "Scotch Premium",
                    style = MaterialTheme.typography.h4)

                Spacer(modifier = Modifier.weight(1f))

                Text(
                    text = "$1200",
                    style = MaterialTheme.typography.body1,
                    fontSize = 25.sp
                )

            }

            Divider(color = Color.Gray)

            Row(modifier = Modifier.padding(top = 40.dp)) {
                ColorsInfoColumn(
                    modifier = Modifier.weight(1f),
                    listOfColors = listOfColors)

                Spacer(modifier = Modifier.width(10.dp))

                SizeColumnInfo(
                    modifier = Modifier.weight(1f),
                    listOfSizes = listOfSizes)
            }

            Spacer(modifier = Modifier.weight(1f))

            Button(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(4.dp),
                onClick = { /*TODO*/ }) {
                Text(text = "Add To Card",
                style = MaterialTheme.typography.button
                )
            }

        }
    }
}

@Composable
fun ColorsInfoColumn(
    modifier: Modifier,
    listOfColors: List<Color>) {
    Column(modifier = modifier) {
        Text(
            modifier = Modifier.padding(start = 5.dp),
            text = "Color",
            style = MaterialTheme.typography.h6
        )
        LazyRow {
            items(listOfColors){ colors ->

                Box(modifier = Modifier.padding(horizontal = 5.dp)) {
                    Box(
                        modifier = Modifier
                            .size(32.dp)
                            .padding(horizontal = 4.dp)
                            .background(
                                color = colors,
                                shape = CircleShape
                            )
                    )
                }
            }
        }
    }
}

@Composable
fun SizeColumnInfo(
    modifier: Modifier,
    listOfSizes: List<String>) {
    Column(modifier = modifier) {
        Text(
            modifier = Modifier.padding(start = 5.dp),
            text = "Size",
            style = MaterialTheme.typography.h6
        )
        LazyRow {

            items(listOfSizes){ sizes ->

                Box(modifier = Modifier.padding(horizontal = 5.dp)) {
                    Box(
                        modifier = Modifier
                            .size(32.dp)
                            .padding(horizontal = 4.dp)
                            .background(
                                color = Color.Gray,
                                shape = CircleShape
                            )
                    ) {
                        Text(
                            modifier = Modifier.align(Alignment.Center),
                            text = sizes)
                    }
                }
            }
        }
    }
}

@Composable
@Preview
fun ProductViewPreview(){
    ProductViewScreen(
        index = "",
        state = ProductViewStates(),
        onEvent = {}
    )
}