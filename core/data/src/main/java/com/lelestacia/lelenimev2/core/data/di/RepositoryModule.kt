package com.lelestacia.lelenimev2.core.data.di

import android.content.Context
import com.lelestacia.lelenimev2.core.data.repository.sharedRepository
import com.lelestacia.lelenimev2.core.data.repository.sharedRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    @Singleton
    fun provideCoreRepository(
        @ApplicationContext context: Context
    ): sharedRepository {
        return sharedRepositoryImpl(
            context = context
        )
    }
}