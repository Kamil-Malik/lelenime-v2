package com.lelestacia.lelenimev2.feature.anime_image.domain.model.model

import android.os.Parcelable
import com.squareup.moshi.JsonClass
import kotlinx.parcelize.Parcelize

@Parcelize
@JsonClass(generateAdapter = true)
data class WaifuArtist(
    val artistId: Int,
    val deviantArt: String?,
    val name: String,
    val patreon: String?,
    val pixiv: String?,
    val twitter: String?
): Parcelable
