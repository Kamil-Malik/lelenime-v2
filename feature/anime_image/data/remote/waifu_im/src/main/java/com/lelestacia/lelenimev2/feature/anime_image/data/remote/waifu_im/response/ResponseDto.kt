package com.lelestacia.lelenimev2.feature.anime_image.data.remote.waifu_im.response


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import androidx.annotation.Keep

@Keep
@JsonClass(generateAdapter = true)
data class ResponseDto(
    @Json(name = "images")
    val images: List<WaifuImageDto>
)