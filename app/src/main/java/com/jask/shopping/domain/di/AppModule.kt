package com.jask.shopping.domain.di

import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.firestore
import com.jask.shopping.domain.repository.AuthRepository
import com.jask.shopping.domain.repository.AuthRepositoryImpl
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
    fun providesRepositoryImpl(firebaseAuth: FirebaseAuth): AuthRepository {
        return AuthRepositoryImpl(
            firebaseAuth,
            firestore = Firebase.firestore
        )
    }

    /*@Singleton
    @Provides
    fun providesFirebaseFireStoreDatabase() = Firebase.firestore*/
}