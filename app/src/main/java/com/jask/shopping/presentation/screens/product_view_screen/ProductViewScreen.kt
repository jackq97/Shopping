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
fun ProductViewScreen() {

    val pagerState = rememberPagerState()

    val listOfColors = listOf(Color.Black, Color.Red, Color.Gray, Color.Blue, Color.Magenta, Color.Yellow)
    val listOfSizes = listOf("XL", "S", "M", "L")

    data class Page(val url: String)

    val pages = listOf(
        Page("https://cdn.pixabay.com/photo/2014/02/19/20/39/winter-270160_1280.jpg"),
        Page("https://cdn.pixabay.com/photo/2019/11/23/03/08/valley-4646114_1280.jpg"),
        Page("https://cdn.pixabay.com/photo/2018/11/29/20/01/nature-3846403_1280.jpg"),
        Page("https://cdn.pixabay.com/photo/2016/11/19/14/38/camel-1839616_1280.jpg"),
        Page("https://cdn.pixabay.com/photo/2014/07/23/00/56/moon-399834_1280.jpg"),
        Page("https://cdn.pixabay.com/photo/2019/12/14/18/28/sunrise-4695484_1280.jpg"),
        Page("https://cdn.pixabay.com/photo/2018/03/29/07/35/water-3271579_1280.jpg"),
        Page("https://cdn.pixabay.com/photo/2021/01/23/13/01/hills-5942468_1280.jpg"),
        Page("https://cdn.pixabay.com/photo/2019/10/09/20/18/etretat-4538160_1280.jpg"),
    )

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
                                model = pages[page].url,
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

            Row(
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

            Row {
                ColorsInfoColumn(
                    modifier = Modifier.weight(1f),
                    listOfColors = listOfColors)

                Spacer(modifier = Modifier.width(10.dp))

                SizeColumnInfo(
                    modifier = Modifier.weight(1f),
                    listOfSizes = listOfSizes)
            }


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
    ProductViewScreen()
}