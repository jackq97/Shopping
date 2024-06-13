package com.jask.shopping.data.paging

import android.util.Log
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

    override suspend fun load(params: LoadParams<QuerySnapshot>): LoadResult<QuerySnapshot, Product> {
        return try {
            val currentPage = params.key ?: queryProductsByName.get().await()

            // Check if the current page has any documents
            if (currentPage.isEmpty) {
                // Return an empty page if there are no documents
                return LoadResult.Page(
                    data = emptyList(),
                    prevKey = null,
                    nextKey = null
                )
            }

            val lastVisibleProduct = currentPage.documents[currentPage.size() - 1]
            val nextPage = queryProductsByName.startAfter(lastVisibleProduct).get().await()

            LoadResult.Page(
                data = currentPage.toObjects(Product::class.java),
                prevKey = null,
                nextKey = nextPage
            )
        } catch (e: Exception) {
            Log.d("TAG", "load: error $e")
            LoadResult.Error(e)
        }
    }
}
