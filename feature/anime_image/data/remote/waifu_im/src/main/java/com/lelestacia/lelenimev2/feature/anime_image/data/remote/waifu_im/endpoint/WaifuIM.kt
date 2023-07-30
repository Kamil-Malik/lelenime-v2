package com.lelestacia.lelenimev2.feature.anime_image.data.remote.waifu_im.endpoint

import com.lelestacia.lelenimev2.feature.anime_image.data.remote.waifu_im.response.ResponseDto
import retrofit2.http.GET
import retrofit2.http.Query

interface WaifuIM {

    @GET("search")
    suspend fun getWaifus(
        @Query("is_nsfw") isNsfw: Boolean? = null,
        @Query("orientation") orientation: String = "PORTRAIT",
        @Query("many") many: Boolean = true
    ): ResponseDto
}