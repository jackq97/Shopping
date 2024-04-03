package com.jask.shopping.presentation.screens.home_feed

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.CircularProgressIndicator
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
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.jask.shopping.R
import com.jask.shopping.data.model.Product
import com.jask.shopping.presentation.screens.home_feed.composables.BestDealsView
import com.jask.shopping.presentation.screens.home_feed.composables.TopProductView

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeFeedScreen(
    state: HomeFeedStates,
    onEvent: (HomeFeedEvents) -> Unit,
    onClick: (String)-> Unit,
){

    var selectedTabState by remember { mutableIntStateOf(0) }
    val titles = listOf("Main","Chair","Cupboard","Accessory","")

    val isSearching by remember { mutableStateOf(false) }
    val searchText by remember { mutableStateOf("Search now") }

    val pagingSpecialProducts = state.specialProduct.collectAsLazyPagingItems()
    val pagingBestProducts = state.bestProducts.collectAsLazyPagingItems()
    val pagingBestDeals = state.bestDeals.collectAsLazyPagingItems()

    val refresh = pagingBestProducts.loadState.refresh
    val append = pagingBestProducts.loadState.append

    Surface(modifier = Modifier.fillMaxSize()) {

        pagingBestProducts.loadState.apply {
            when {
                refresh is LoadState.Loading -> ProgressBar()
                refresh is LoadState.Error -> print(refresh)
                append is LoadState.Loading -> ProgressBar()
                append is LoadState.Error -> print(append)
            }
        }

        Column{

            SearchBar(
                query = searchText,//text showed on SearchBar
                onQueryChange = {}, //update the value of searchText
                onSearch = {}, //the callback to be invoked when the input service triggers the ImeAction.Search action
                active = isSearching, //whether the user is searching or not
                onActiveChange = { }, //the callback to be invoked when this search bar's active state is changed
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Default.Search,
                        contentDescription = null
                    )
                },
                trailingIcon = {
                    Icon(
                        painterResource(id = R.drawable.mic),
                        contentDescription = null
                    )
                }
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
            
            LazyVerticalGrid(
                modifier = Modifier.padding(
                    start = 12.dp,
                    top = 12.dp
                ),
                columns = GridCells.Fixed(2)) {

                item(span = {GridItemSpan(maxCurrentLineSpan)}) {
                    HomeFeedSpecialProductLazyRow(
                        specialProducts = pagingSpecialProducts,
                        onClick = onClick
                    )
                }

                item(span = {GridItemSpan(maxCurrentLineSpan)}) {
                    DealsDividedText(text = "Best Deals")
                }

                item(span = {GridItemSpan(maxCurrentLineSpan)}) {
                    HomeFeedBestDealsProductLazyRow(
                        bestDeals = pagingBestDeals,
                        onClick = onClick
                    )
                }

                item(span = {GridItemSpan(maxCurrentLineSpan)}) {
                    DealsDividedText(text = "Best Products")
                }

                items(count = pagingBestProducts.itemCount) { index ->

                    BestDealsView(
                        imageUrl = pagingBestProducts[index]!!.images[0],
                        title = pagingBestProducts[index]!!.name,
                        price = pagingBestProducts[index]!!.price.toString(),
                        index = index,
                        onClick = onClick
                    )
                }
            }
        }
    }
}

@Composable
fun ProgressBar() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ){
        CircularProgressIndicator()
    }
}

@Composable
fun HomeFeedBestDealsProductLazyRow(
    bestDeals: LazyPagingItems<Product>,
    onClick:(String) -> Unit
) {
    LazyRow(modifier = Modifier) {

        items(count = bestDeals.itemCount){index ->
            TopProductView(
                imageUrl = bestDeals[index]!!.images[0],
                title = bestDeals[index]!!.name,
                price = bestDeals[index]!!.price.toString(),
                index = index,
                onClick = onClick
            )
        }
    }
}

@Composable
fun HomeFeedSpecialProductLazyRow(
    specialProducts: LazyPagingItems<Product>,
    onClick:(String) -> Unit
){
    LazyRow(modifier = Modifier,
    ) {

        items(count = specialProducts.itemCount){index ->

            TopProductView(
                imageUrl = specialProducts[index]!!.images[0],
                title = specialProducts[index]!!.name,
                price = specialProducts[index]!!.price.toString(),
                index = index,
                onClick = onClick
            )
        }
    }
}

@Composable fun DealsDividedText(
    text: String
){
    Column(
        modifier = Modifier
            .padding(vertical = 12.dp),
        horizontalAlignment = Alignment.CenterHorizontally) {
        Text(
            text = text,
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.Bold,
        )
    }
}

@Preview(showBackground = true)
@Composable
fun HomeFeedScreenPreview(){
    HomeFeedScreen(
        state = HomeFeedStates(),
        onEvent = {},
        onClick = {}
    )
}