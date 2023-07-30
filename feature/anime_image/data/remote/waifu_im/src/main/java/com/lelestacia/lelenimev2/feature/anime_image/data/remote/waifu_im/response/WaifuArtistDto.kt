package com.lelestacia.lelenimev2.feature.anime_image.data.remote.waifu_im.response


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import androidx.annotation.Keep

@Keep
@JsonClass(generateAdapter = true)
data class WaifuArtistDto(
    @Json(name = "artist_id")
    val artistId: Int,
    @Json(name = "deviant_art")
    val deviantArt: String?,
    @Json(name = "name")
    val name: String,
    @Json(name = "patreon")
    val patreon: String?,
    @Json(name = "pixiv")
    val pixiv: String?,
    @Json(name = "twitter")
    val twitter: String?
)