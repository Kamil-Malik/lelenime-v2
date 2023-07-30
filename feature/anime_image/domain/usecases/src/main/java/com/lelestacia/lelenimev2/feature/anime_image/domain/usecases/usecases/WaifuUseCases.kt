package com.lelestacia.lelenimev2.feature.anime_image.domain.usecases.usecases

import com.lelestacia.lelenimev2.core.utils.DataState
import com.lelestacia.lelenimev2.feature.anime_image.data.repository.repository.WaifuRepository
import com.lelestacia.lelenimev2.feature.anime_image.domain.model.model.WaifuImage
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class WaifuUseCases @Inject constructor(
    private val waifuRepository: WaifuRepository
) {

    fun getWaifus(): Flow<DataState<List<WaifuImage>>> {
        return waifuRepository.getWaifus()
    }

    fun encodeJson(image: WaifuImage): String {
        val moshi = Moshi.Builder().build()
        val jsonAdapter: JsonAdapter<WaifuImage> = moshi.adapter(WaifuImage::class.java)
        return jsonAdapter.toJson(image)
    }

    fun decodeJson(image:String): WaifuImage {
        val moshi = Moshi.Builder().build()
        val jsonAdapter: JsonAdapter<WaifuImage> = moshi.adapter(WaifuImage::class.java)
        return jsonAdapter.fromJson(image) ?: throw Exception("Sorry, we failed to retrieve image from previous screen")
    }
}