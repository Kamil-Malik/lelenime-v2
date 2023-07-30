package com.lelestacia.lelenimev2.feature.anime_image.data.repository.repository

import com.lelestacia.lelenimev2.core.utils.DataState
import com.lelestacia.lelenimev2.feature.anime_image.domain.model.model.WaifuImage
import kotlinx.coroutines.flow.Flow

interface WaifuRepository {
    fun getWaifus(): Flow<DataState<List<WaifuImage>>>
}