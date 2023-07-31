package com.lelestacia.lelenimev2.core.common.worker

import android.annotation.SuppressLint
import android.app.NotificationChannel
import android.app.NotificationChannelGroup
import android.app.NotificationManager
import android.content.ContentValues
import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.lelestacia.lelenimev2.core.common.Constants
import com.lelestacia.lelenimev2.core.common.R
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import timber.log.Timber
import java.io.InputStream
import java.net.HttpURLConnection
import java.net.URL
import java.util.Random
import kotlin.properties.Delegates

class ImageDownloadWorker(
    private val context: Context,
    private val params: WorkerParameters
) : CoroutineWorker(context, params) {

    private var fileID by Delegates.notNull<Int>()

    private val channelID = Constants.NOTIFICATION_CHANNEL_IMAGE_DOWNLOAD_ID
    private val channelName = Constants.NOTIFICATION_CHANNEL_IMAGE_DOWNLOAD_NAME
    private val notificationManager: NotificationManagerCompat by lazy {
        NotificationManagerCompat.from(context)
    }

    init {
        createNotificationGroupAndChannel()
    }

    private fun createNotificationGroupAndChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                if (notificationManager.getNotificationChannelGroup(Constants.NOTIFICATION_GROUP_SYSTEM_SERVICE_ID) == null) {
                    val notificationGroup = NotificationChannelGroup(
                        /* id = */ Constants.NOTIFICATION_GROUP_SYSTEM_SERVICE_ID,
                        /* name = */ Constants.NOTIFICATION_GROUP_SYSTEM_SERVICE_NAME
                    )
                    notificationManager.createNotificationChannelGroup(notificationGroup)
                }
            }

            val notificationChannel =
                NotificationChannel(channelID, channelName, NotificationManager.IMPORTANCE_LOW)
                    .apply {
                        description = "Notifications for download progress"

                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                            // Assign the channel to the group
                            group = Constants.NOTIFICATION_GROUP_SYSTEM_SERVICE_ID
                        }
                    }

            notificationManager.createNotificationChannel(notificationChannel)
        }
    }

    override suspend fun doWork(): Result {
        return withContext(Dispatchers.IO) {
            try {
                //  Retrieve File Name and File URL from worker input data
                fileID = params.inputData.getInt(FILE_ID, Random().nextInt())
                val fileName = params.inputData.getString(FILE_NAME).orEmpty()
                val url = URL(params.inputData.getString(DOWNLOAD_URL).orEmpty())

                //  Start a connection based on retrieved URL
                val connection: HttpURLConnection = url.openConnection() as HttpURLConnection
                connection.doInput = true
                connection.connect()
                val inputStream: InputStream = connection.inputStream
                val totalBytes = connection.contentLength.toLong()
                var downloadedBytes: Long = 0

                // Save the image in the public media storage using MediaStore
                val contentValues = ContentValues().apply {
                    put(MediaStore.Images.Media.DISPLAY_NAME, fileName)
                    put(MediaStore.Images.Media.MIME_TYPE, "image/jpeg")
                    put(MediaStore.Images.Media.IS_PENDING, 1)
                    put(MediaStore.Images.Media.RELATIVE_PATH, "Pictures/Lelenime")
                }

                val contentResolver = applicationContext.contentResolver
                val bitmap: Bitmap = BitmapFactory.decodeStream(inputStream)

                // Use EXTERNAL_CONTENT_URI for versions lower than Q
                val collection: Uri = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                    MediaStore.Images.Media.getContentUri(MediaStore.VOLUME_EXTERNAL_PRIMARY)
                } else {
                    MediaStore.Images.Media.EXTERNAL_CONTENT_URI
                }

                val imageUri = contentResolver.insert(collection, contentValues)

                // Show progress notification
                showProgressNotification()

                imageUri?.let { uri ->
                    val bufferSize = 4096
                    val buffer = ByteArray(bufferSize)
                    var bytesRead: Int

                    while (inputStream.read(buffer).also { bytesRead = it } > 0) {
                        downloadedBytes += bytesRead.toLong()
                        val progress = (downloadedBytes * 100 / totalBytes).toInt()

                        // Update the progress notification
                        updateProgressNotification(progress)
                    }

                    contentResolver.openOutputStream(uri)?.use { outputStream ->
                        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream)
                    }

                    // Download completed, remove the progress bar from the notification
                    contentValues.clear()
                    contentValues.put(MediaStore.Images.Media.IS_PENDING, 0)
                    contentResolver.update(uri, contentValues, null, null)
                }

                clearProgressNotification()
                showCompletionNotification()

                Result.success()
            } catch (e: Exception) {
                Timber.e(LOGGER_NAME + e.stackTraceToString())

                // Show an error notification
                showErrorNotification()

                Result.failure()
            }
        }
    }

    @SuppressLint("MissingPermission")
    private fun showProgressNotification() {
        val notificationId = 1
        val notificationBuilder = NotificationCompat.Builder(applicationContext, channelID)
            .setContentTitle("Image Download")
            .setContentText("Downloading...")
            .setSmallIcon(R.drawable.ic_download)
            .setProgress(100, 0, true)
            .setOngoing(true)

        notificationManager.notify(notificationId, notificationBuilder.build())
    }

    @SuppressLint("MissingPermission")
    private fun updateProgressNotification(progress: Int) {
        val notificationId = 1
        val notificationBuilder = NotificationCompat.Builder(applicationContext, channelID)
            .setContentTitle("Image Download")
            .setContentText("Downloading...")
            .setSmallIcon(R.drawable.ic_download)
            .setProgress(100, progress, false)
            .setOngoing(true)

        notificationManager.notify(notificationId, notificationBuilder.build())
    }

    private fun clearProgressNotification() {
        val notificationId = 1
        notificationManager.cancel(notificationId)
    }

    @SuppressLint("MissingPermission")
    private fun showCompletionNotification() {
        val fileName = inputData.getString(FILE_NAME).orEmpty()

        val notificationBuilder = NotificationCompat.Builder(applicationContext, channelID)
            .setContentTitle("Download Completed")
            .setContentText("Image $fileName successfully downloaded")
            .setSmallIcon(R.drawable.ic_download_finished)
            .setAutoCancel(true)

        notificationManager.notify(fileID, notificationBuilder.build())
    }

    @SuppressLint("MissingPermission")
    private fun showErrorNotification() {
        val notificationId = 1
        val notificationBuilder = NotificationCompat.Builder(applicationContext, channelID)
            .setContentTitle("Image Download")
            .setContentText("Download failed")
            .setSmallIcon(R.drawable.ic_error_filled)
            .setAutoCancel(true)

        notificationManager.notify(notificationId, notificationBuilder.build())
    }

    companion object {
        private const val LOGGER_NAME = "Image Download Worker: "
        const val DOWNLOAD_URL = "download_url"
        const val FILE_ID = "file_id"
        const val FILE_NAME = "filen_ame"
    }
}