package com.jask.shopping.presentation.screens.home_feed

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.jask.shopping.R
import com.jask.shopping.presentation.screens.home_feed.composables.BestDealsView
import com.jask.shopping.presentation.screens.home_feed.composables.ProductView
import com.jask.shopping.presentation.screens.home_feed.composables.TopProductView
import com.jask.shopping.presentation.screens.register_screen.RegisterEvents

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeFeedScreen(
    state: HomeFeedStates,
    onEvent: (HomeFeedEvents) -> Unit
){

    var selectedTabState by remember { mutableIntStateOf(0) }
    val scrollState = rememberScrollState()
    val titles = listOf("Main","Chair","Cupboard","Accessory","")

    val isSearching by remember { mutableStateOf(false) }
    val searchText by remember { mutableStateOf("Search now") }

    Surface(modifier = Modifier.fillMaxSize()) {

        Column(
            modifier = Modifier.verticalScroll(scrollState),
            horizontalAlignment = Alignment.CenterHorizontally
            ) {
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
                selectedTabIndex = selectedTabState,
                edgePadding = 0.dp
            ) {
                titles.forEachIndexed { index, title ->
                    Tab(
                        selected = selectedTabState == index,
                        onClick = { selectedTabState = index },
                        text = {
                            Text(
                                text = title
                            )
                        }
                    )
                }
            }

            HomeFeedSpecialProductLazyRow(state = state)

            DealsDividedText(text = "Best Deals")

            HomeFeedBestDealsProductLazyRow(state = state)

            DealsDividedText(text = "Best Products")

            HomeFeedBestProductLazyRow(state = state)

        }
    }
}

@Composable
fun HomeFeedSpecialProductLazyRow(
    state: HomeFeedStates
){
    LazyRow(modifier = Modifier
        .padding(10.dp),
    ) {

        items(items = state.specialProduct!!) { data ->

            TopProductView(
                imageUrl = data.images[0],
                title = data.name,
                price = data.price.toString()
            )
        }
    }
}

@Composable
fun HomeFeedBestDealsProductLazyRow(
    state: HomeFeedStates
){
    LazyRow(modifier = Modifier
        .padding(10.dp),
    ) {

        items(items = state.bestDeals!!) { data ->

            ProductView(
                imageUrl = data.images[0],
                title = data.name,
                price = data.price.toString()
            )
        }
    }
}

@Composable
fun HomeFeedBestProductLazyRow(
    state: HomeFeedStates
){
    LazyRow(modifier = Modifier
        .padding(10.dp),
    ) {

        items(items = state.bestProducts!!) { data ->

            BestDealsView(
                imageUrl = data.images[0],
                title = data.name,
                price = data.price.toString()
            )
        }
    }
}

@Composable
fun DealsDividedText(
    text: String
){
    Text(
        modifier = Modifier
            .padding(10.dp),
        text = text,
        style = MaterialTheme.typography.headlineMedium,
        fontWeight = FontWeight.Bold
    )
}

@Preview(showBackground = true)
@Composable
fun HomeFeedScreenPreview(){
    HomeFeedScreen(
        state = HomeFeedStates(),
        onEvent = {})
}