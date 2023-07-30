package com.lelestacia.lelenimev2.feature.anime_image.domain.model.model

import android.os.Parcelable
import com.squareup.moshi.JsonClass
import kotlinx.parcelize.Parcelize

@Parcelize
@JsonClass(generateAdapter = true)
data class WaifuTag(
    val description: String,
    val isNsfw: Boolean,
    val name: String,
    val tagId: Int
) : Parcelable
