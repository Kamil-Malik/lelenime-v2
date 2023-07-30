package com.lelestacia.lelenimev2.feature.anime_image.data.repository.di

import com.lelestacia.lelenimev2.feature.anime_image.data.remote.waifu_im.endpoint.WaifuIM
import com.lelestacia.lelenimev2.feature.anime_image.data.repository.repository.WaifuRepository
import com.lelestacia.lelenimev2.feature.anime_image.data.repository.repository.WaifuRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    @Singleton
    fun provideWaifuRepository(
        waifuIM: WaifuIM
    ): WaifuRepository {
        return WaifuRepositoryImpl(
            waifuIM = waifuIM
        )
    }
}