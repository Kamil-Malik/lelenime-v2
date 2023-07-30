package com.lelestacia.lelenimev2.feature.anime_image.data.remote.waifu_im.di

import com.lelestacia.lelenimev2.feature.anime_image.data.remote.waifu_im.endpoint.WaifuIM
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    @Singleton
    fun provideWaifuIM(): WaifuIM {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(MoshiConverterFactory.create())
            .build()
            .create(WaifuIM::class.java)
    }

    private const val BASE_URL = "https://api.waifu.im/"
}