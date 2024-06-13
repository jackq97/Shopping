package com.jask.shopping.di

import androidx.paging.PagingConfig
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.firestore
import com.jask.shopping.data.paging.ProductsPagingSource
import com.jask.shopping.repository.AuthRepository
import com.jask.shopping.repository.AuthRepositoryImpl
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
    @Singleton
    fun providesFirebaseAuth() = FirebaseAuth.getInstance()

    @Singleton
    @Provides
    fun providesRepositoryImpl(
        firebaseAuth: FirebaseAuth,
        config: PagingConfig,
    ): AuthRepository {
        return AuthRepositoryImpl(
            firebaseAuth = firebaseAuth,
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
        pageSize = PAGE_SIZE.toInt()
    )

}