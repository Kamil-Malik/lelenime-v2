package com.lelestacia.lelenimev2.feature.anime_image.domain.model.type

import android.os.Build
import android.os.Bundle
import androidx.navigation.NavType
import com.lelestacia.lelenimev2.feature.anime_image.domain.model.model.WaifuImage
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi

class WaifuImageNavType : NavType<WaifuImage>(isNullableAllowed = true) {

    override fun get(bundle: Bundle, key: String): WaifuImage? {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            bundle.getParcelable(key, WaifuImage::class.java)
        } else {
            bundle.getParcelable(key)
        }
    }

    override fun parseValue(value: String): WaifuImage {
        val moshi = Moshi.Builder().build()
        val jsonAdapter: JsonAdapter<WaifuImage> = moshi.adapter(WaifuImage::class.java).lenient()
        return jsonAdapter.fromJson(value) ?: throw Exception("Sorry, we failed to retrieve image from previous screen")
    }

    override fun put(bundle: Bundle, key: String, value: WaifuImage) {
        bundle.putParcelable(key, value)
    }
}