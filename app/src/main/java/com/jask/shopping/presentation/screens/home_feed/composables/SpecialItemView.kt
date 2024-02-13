package com.jask.shopping.presentation.screens.home_feed.composables

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun SpecialItemView(){


    Surface(modifier = Modifier
        .fillMaxSize()
        .background(MaterialTheme.colorScheme.background)
    ) {

        Column {
            Card(modifier = Modifier
                .width(270.dp)
                .height(180.dp)) {
                Row() {
                    Image(
                        modifier = Modifier
                            .weight(1.5f)
                            .fillMaxSize(),
                        imageVector = Icons.Default.Add,
                        contentDescription = null
                    )

                    Column(
                        modifier = Modifier
                            .weight(1f)
                            .fillMaxSize()
                    ) {
                        IconButton(
                            modifier = Modifier.align(Alignment.End),
                            onClick = { /*TODO*/ }) {
                            Icon(imageVector = Icons.Default.CheckCircle, contentDescription = "")
                        }

                        Text(
                            maxLines = 2,
                            text = "Scotch Premium",
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold
                        )

                        Text(
                            modifier = Modifier.padding(top = 10.dp),
                            text = "$1000",
                                    style = MaterialTheme.typography.labelLarge,
                        )

                        Box(modifier = Modifier.fillMaxSize()) {
                            Button(
                                modifier = Modifier
                                    .padding(bottom = 2.dp, end = 5.dp)
                                    .align(Alignment.BottomEnd)
                                    .fillMaxWidth(),
                                    contentPadding = PaddingValues(0.dp),
                                onClick = { /*TODO*/ }) {
                                Text(
                                    maxLines = 1,
                                    text = "Add to Cart"
                                )
                            }
                        }
                    }
                }
            }

            ProductView()
        }
    }
}

@Composable
fun ProductView(){
    Row(modifier = Modifier
        .fillMaxWidth()
        .height(100.dp)
    ) {

        Image(
            modifier = Modifier
                .weight(0.8f)
                .fillMaxSize(),
            imageVector = Icons.Default.Add, contentDescription = "")

        Column(modifier = Modifier
            .fillMaxSize()
            .weight(1f)
            .padding(top = 18.dp),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.Start
        ) {

            Text(text = "Scotch Premium",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold)

            Row {

                Text(text = "$1600",
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
                onClick = { /*TODO*/ }) {
                Text(text = "See product",
                    style = MaterialTheme.typography.labelLarge
                )
            }
        }
    }
}

@Preview
@Composable
fun SpecialItemViewPreview() {
    SpecialItemView()
}