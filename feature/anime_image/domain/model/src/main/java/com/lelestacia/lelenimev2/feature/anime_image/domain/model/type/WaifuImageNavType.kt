package com.lelestacia.lelenimev2.feature.anime_image.domain.model.type

import android.os.Build
import android.os.Bundle
import androidx.navigation.NavType
import com.lelestacia.lelenimev2.core.utils.fromJson
import com.lelestacia.lelenimev2.core.utils.parcelable
import com.lelestacia.lelenimev2.feature.anime_image.domain.model.model.WaifuImage
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi

class WaifuImageNavType : NavType<WaifuImage>(isNullableAllowed = true) {

    override fun get(bundle: Bundle, key: String): WaifuImage? {
        return bundle.parcelable(key)
    }

    override fun parseValue(value: String): WaifuImage {
        return fromJson(value)
    }

    override fun put(bundle: Bundle, key: String, value: WaifuImage) {
        bundle.putParcelable(key, value)
    }
}