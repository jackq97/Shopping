package com.jask.shopping.data.model.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.QuerySnapshot
import com.jask.shopping.data.model.Product
import kotlinx.coroutines.tasks.await

class ProductsPagingSource (
    private val queryProductsByName: Query
) : PagingSource<QuerySnapshot, Product>() {
    override fun getRefreshKey(state: PagingState<QuerySnapshot, Product>): QuerySnapshot? = null

    override suspend fun load(params: LoadParams<QuerySnapshot>): LoadResult<QuerySnapshot, Product> = try {
        val currentPage = params.key ?: queryProductsByName.get().await()
        val lastVisibleProduct = currentPage.documents[currentPage.size() - 1]
        val nextPage = queryProductsByName.startAfter(lastVisibleProduct).get().await()
        LoadResult.Page(
            data = currentPage.toObjects(Product::class.java),
            prevKey = null,
            nextKey = nextPage
        )
    } catch (e: Exception) {
        LoadResult.Error(e)
    }
}