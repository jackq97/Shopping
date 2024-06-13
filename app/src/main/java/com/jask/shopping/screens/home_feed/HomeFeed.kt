package com.jask.shopping.screens.home_feed

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.log
import com.jask.shopping.R
import com.jask.shopping.data.model.Product
import com.jask.shopping.screens.home_feed.composables.BestDealsView
import com.jask.shopping.screens.home_feed.composables.ProductView
import com.jask.shopping.screens.home_feed.composables.TopProductView

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeFeedScreen(
    state: HomeFeedStates,
    onEvent: (HomeFeedEvents) -> Unit,
    onClick: (String, String)-> Unit,
){

    var selectedTabState by remember { mutableIntStateOf(0) }
    val titles = listOf("Main","Chair","Cupboard","Accessory")

    val isSearching by remember { mutableStateOf(false) }
    var searchText by remember { mutableStateOf("") }

    val pagingSpecialProducts = state.specialProduct.collectAsLazyPagingItems()
    val pagingBestDeals = state.bestDeals.collectAsLazyPagingItems()
    val pagingBestProducts = state.bestProducts.collectAsLazyPagingItems()

    Surface(modifier = Modifier.fillMaxSize()) {

        Column{

            SearchBar(
                query = searchText,
                onQueryChange = {
                    searchText = it
                },
                onSearch = {},
                active = isSearching,
                onActiveChange = { },
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

                columns = GridCells.Fixed(2)) {

                item (span = {GridItemSpan(maxCurrentLineSpan)}) {
                    Spacer(modifier = Modifier.height(8.dp))
                }

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

                pagingBestProducts.apply {
                    when {
                        loadState.refresh is LoadState.Loading || loadState.append is LoadState.Loading -> {
                            Log.d("TAG", "HomeFeedScreen: loading")
                            item(span = {GridItemSpan(maxCurrentLineSpan)}) {
                                Box(
                                    modifier = Modifier.fillMaxWidth(),
                                    contentAlignment = Alignment.Center
                                ) {
                                    CircularProgressIndicator(
                                        modifier = Modifier.align(Alignment.Center)
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun HomeFeedSpecialProductLazyRow(
    specialProducts: LazyPagingItems<Product>,
    onClick:(String, String) -> Unit
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

@Composable
fun HomeFeedBestDealsProductLazyRow(
    bestDeals: LazyPagingItems<Product>,
    onClick:(String, String) -> Unit
) {
    LazyRow(modifier = Modifier) {

        items(count = bestDeals.itemCount){index ->
            ProductView(
                imageUrl = bestDeals[index]!!.images[0],
                title = bestDeals[index]!!.name,
                price = bestDeals[index]!!.price.toString(),
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
        onClick = { _, _ -> }
    )
}