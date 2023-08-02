package com.lelestacia.lelenimev2.core.data.repository

interface sharedRepository {
    fun launchDownloadImage(
        imageUrl: String,
        imageID: Int,
        imageName: String
    )
}