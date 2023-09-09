package com.lelestacia.lelenimev2.core.utils

import com.squareup.moshi.Moshi

inline fun <reified T> toJson(klass: T): String {
    val moshi = Moshi.Builder().build()
    val adapter = moshi.adapter(T::class.java)
    return adapter.toJson(klass)
}

inline fun <reified T> fromJson(raw: String): T {
    val moshi = Moshi.Builder().build()
    val adapter = moshi.adapter(T::class.java)
    return adapter.fromJson(raw) ?: throw Exception("Failed to parse the JSON")
}