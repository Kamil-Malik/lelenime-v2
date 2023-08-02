package com.lelestacia.lelenimev2.feature.anime_image.domain.usecases.di

import com.lelestacia.lelenimev2.core.data.repository.sharedRepository
import com.lelestacia.lelenimev2.feature.anime_image.data.repository.repository.WaifuRepository
import com.lelestacia.lelenimev2.feature.anime_image.domain.usecases.usecases.WaifuUseCases
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
object UseCasesModule {

    @Provides
    @ViewModelScoped
    fun provideWaifuUseCases(
        waifuRepository: WaifuRepository,
        sharedRepository: sharedRepository
    ): WaifuUseCases {
        return WaifuUseCases(
            waifuRepository = waifuRepository,
            sharedRepository = sharedRepository
        )
    }
}