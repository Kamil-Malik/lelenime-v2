package com.lelestacia.lelenimev2.feature.anime_image.domain.usecases.usecases

import com.lelestacia.lelenimev2.core.data.repository.sharedRepository
import com.lelestacia.lelenimev2.core.utils.DataState
import com.lelestacia.lelenimev2.core.utils.fromJson
import com.lelestacia.lelenimev2.core.utils.toJson
import com.lelestacia.lelenimev2.feature.anime_image.data.repository.repository.WaifuRepository
import com.lelestacia.lelenimev2.feature.anime_image.domain.model.model.WaifuImage
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class WaifuUseCases @Inject constructor(
    private val waifuRepository: WaifuRepository,
    private val sharedRepository: sharedRepository
) {

    fun getWaifus(): Flow<DataState<List<WaifuImage>>> {
        return waifuRepository.getWaifus()
    }

    fun downloadWaifus(image: WaifuImage) {
        sharedRepository.launchDownloadImage(
            imageUrl = image.url,
            imageID = image.imageId,
            imageName = image.imageId.toString()
        )
    }

    fun encodeJson(image: WaifuImage): String {
        return toJson(image)
    }

    fun decodeJson(image: String): WaifuImage {
        return fromJson(image)
    }
}