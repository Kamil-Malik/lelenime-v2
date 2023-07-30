package com.lelestacia.lelenimev2.feature.anime_image.data.remote.waifu_im.response


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import androidx.annotation.Keep

@Keep
@JsonClass(generateAdapter = true)
data class WaifuTagDto(
    @Json(name = "description")
    val description: String,
    @Json(name = "is_nsfw")
    val isNsfw: Boolean,
    @Json(name = "name")
    val name: String,
    @Json(name = "tag_id")
    val tagId: Int
)