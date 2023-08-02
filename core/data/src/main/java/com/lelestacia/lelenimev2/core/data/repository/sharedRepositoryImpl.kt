package com.lelestacia.lelenimev2.core.data.repository

import android.content.Context
import androidx.work.Constraints
import androidx.work.Data
import androidx.work.NetworkType
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import com.lelestacia.lelenimev2.core.data.worker.ImageDownloadWorker
import javax.inject.Inject

class sharedRepositoryImpl @Inject constructor(
    private val context: Context
) : sharedRepository {

    override fun launchDownloadImage(imageUrl: String, imageID: Int, imageName: String) {
        val inputData = Data.Builder()
            .putString(
                ImageDownloadWorker.DOWNLOAD_URL,
                imageUrl
            )
            .putInt(
                ImageDownloadWorker.FILE_ID,
                imageID
            )
            .putString(
                ImageDownloadWorker.FILE_NAME,
                imageName
            )
            .build()

        val constraints = Constraints.Builder()
            .setRequiredNetworkType(NetworkType.CONNECTED)
            .build()

        val imageDownloadRequest = OneTimeWorkRequestBuilder<ImageDownloadWorker>()
            .setInputData(inputData)
            .setConstraints(constraints)
            .build()

        WorkManager.getInstance(context).enqueue(imageDownloadRequest)
    }
}