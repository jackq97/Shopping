package com.jask.shopping.domain.di

import androidx.paging.PagingConfig
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.firestore
import com.google.firebase.firestore.ktx.firestore
import com.jask.shopping.data.model.paging.ProductsPagingSource
import com.jask.shopping.domain.repository.AuthRepository
import com.jask.shopping.domain.repository.AuthRepositoryImpl
import com.jask.shopping.util.Constants.PAGE_SIZE
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    fun provideQueryProductsByName() = com.google.firebase.ktx.Firebase.firestore
        .collection("Products")
        .whereEqualTo("category", "best products")
        .limit(PAGE_SIZE)

    @Provides
    @Singleton
    fun providesFirebaseAuth() = FirebaseAuth.getInstance()

    @Singleton
    @Provides
    fun providesRepositoryImpl(
        firebaseAuth: FirebaseAuth,
        config: PagingConfig
    ): AuthRepository {
        return AuthRepositoryImpl(
            firebaseAuth,
            firestore = Firebase.firestore,
            config = config
        )
    }

    @Provides
    fun provideProductsPagingSource(
        queryProductsByName: Query
    ) = ProductsPagingSource(
        queryProductsByName = queryProductsByName)

    @Provides
    fun providePagingConfig() = PagingConfig(
        pageSize = PAGE_SIZE.toInt())

}