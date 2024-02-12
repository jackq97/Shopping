package com.jask.shopping.presentation.screens.home_feed

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.ScrollableTabRow
import androidx.compose.material3.SearchBar
import androidx.compose.material3.Surface
import androidx.compose.material3.Tab
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.jask.shopping.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeFeedScreen(){

    var state by remember { mutableIntStateOf(0) }
    val titles = listOf("Main","Chair","Cupboard","Accessory","")

    val isSearching by remember { mutableStateOf(false) }
    val searchText by remember { mutableStateOf("Search now") }

    Surface(modifier = Modifier.fillMaxSize()) {

        Column {
            SearchBar(
                query = searchText,//text showed on SearchBar
                onQueryChange = {}, //update the value of searchText
                onSearch = {}, //the callback to be invoked when the input service triggers the ImeAction.Search action
                active = isSearching, //whether the user is searching or not
                onActiveChange = {  }, //the callback to be invoked when this search bar's active state is changed
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                leadingIcon = { Icon(imageVector = Icons.Default.Search, contentDescription = null) },
                trailingIcon = { Icon(painterResource(id = R.drawable.mic) , contentDescription = null) }
            ) {}

            ScrollableTabRow(
                selectedTabIndex = state,
                edgePadding = 0.dp
            ) {
                titles.forEachIndexed { index, title ->
                    Tab(
                        selected = state == index,
                        onClick = { state = index },
                        text = {
                            Text(
                                text = title
                            )
                        }
                    )
                }
            }

        }
        Column(verticalArrangement = Arrangement.Center) {
            Text(text = "this is home feed")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun HomeFeedScreenPreview(){
    HomeFeedScreen()
}