package com.lelestacia.lelenimev2

import android.app.Activity
import android.content.pm.ActivityInfo
import android.net.Uri
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.viewinterop.AndroidView
import androidx.media3.common.MediaItem
import androidx.media3.common.util.UnstableApi
import androidx.media3.datasource.DefaultDataSource
import androidx.media3.exoplayer.DefaultLoadControl
import androidx.media3.exoplayer.DefaultRenderersFactory
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.exoplayer.hls.HlsMediaSource
import androidx.media3.exoplayer.trackselection.DefaultTrackSelector
import androidx.media3.ui.PlayerView
import com.google.accompanist.systemuicontroller.SystemUiController
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.lelestacia.lelenimev2.ui.theme.LelenimeV2Theme
import dagger.hilt.android.AndroidEntryPoint

@UnstableApi
@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    companion object {
        private const val EPISODE_URL =
            "https://www042.vipanicdn.net/streamhls/94d34c0f1d8a19ee1c50783c68583c7b/ep.1.1688424600.m3u8"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            LelenimeV2Theme {

                val uiController = rememberSystemUiController()

                uiController.isStatusBarVisible = false
                uiController.isNavigationBarVisible = false

                VideoScreen(
                    uiController = uiController,
                    episodeURI = Uri.parse(EPISODE_URL)
                )
            }
        }
    }
}

@UnstableApi
@Composable
fun VideoScreen(
    uiController: SystemUiController,
    episodeURI: Uri
) {
    val context = LocalContext.current

    val newMediaPlayer: ExoPlayer = remember {
        ExoPlayer.Builder(context, DefaultRenderersFactory(context))
            .setTrackSelector(DefaultTrackSelector(context))
            .setLoadControl(DefaultLoadControl())
            .build()
    }

    val dataSourceFactory = remember {
        DefaultDataSource.Factory(context)
    }

    val mediaSource = remember {
        HlsMediaSource
            .Factory(dataSourceFactory)
            .createMediaSource(MediaItem.fromUri(episodeURI))
    }

    AndroidView(
        factory = { localContext ->
            newMediaPlayer.setMediaSource(mediaSource)

            PlayerView(localContext).apply {
                player = newMediaPlayer
            }
        },
        update = {

        },
        modifier = Modifier
            .fillMaxSize()
    )

    DisposableEffect(Unit) {

        (context as Activity).requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE

        onDispose {
            context.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED
            newMediaPlayer.release()
        }
    }
}