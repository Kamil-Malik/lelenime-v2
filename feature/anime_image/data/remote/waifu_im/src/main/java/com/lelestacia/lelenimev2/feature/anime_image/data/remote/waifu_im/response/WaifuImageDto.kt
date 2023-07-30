package com.lelestacia.lelenimev2.feature.anime_image.data.remote.waifu_im.response


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import androidx.annotation.Keep

@Keep
@JsonClass(generateAdapter = true)
data class WaifuImageDto(
    @Json(name = "artist")
    val artist: WaifuArtistDto?,
    @Json(name = "byte_size")
    val byteSize: Int,
    @Json(name = "dominant_color")
    val dominantColor: String,
    @Json(name = "extension")
    val extension: String,
    @Json(name = "favorites")
    val favorites: Int,
    @Json(name = "height")
    val height: Int,
    @Json(name = "image_id")
    val imageId: Int,
    @Json(name = "is_nsfw")
    val isNsfw: Boolean,
    @Json(name = "liked_at")
    val likedAt: String?,
    @Json(name = "preview_url")
    val previewUrl: String,
    @Json(name = "signature")
    val signature: String,
    @Json(name = "source")
    val source: String,
    @Json(name = "tags")
    val tags: List<WaifuTagDto>,
    @Json(name = "uploaded_at")
    val uploadedAt: String,
    @Json(name = "url")
    val url: String,
    @Json(name = "width")
    val width: Int
)