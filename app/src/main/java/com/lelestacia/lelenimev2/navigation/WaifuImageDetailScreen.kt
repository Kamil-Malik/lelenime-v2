package com.lelestacia.lelenimev2.navigation

import android.content.Intent
import android.os.Build
import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.lelestacia.lelenimev2.core.common.screen.ErrorScreen
import com.lelestacia.lelenimev2.core.utils.DataState
import com.lelestacia.lelenimev2.core.utils.Destination
import com.lelestacia.lelenimev2.feature.anime_image.domain.model.model.WaifuImage
import com.lelestacia.lelenimev2.feature.anime_image.domain.model.type.WaifuImageNavType
import com.lelestacia.lelenimev2.feature.anime_image.domain.usecases.viewmodel.WaifuDetailImageViewModel
import com.lelestacia.lelenimev2.feature.anime_image.ui.screen.detail.WaifuDetailImageEvent
import com.lelestacia.lelenimev2.feature.anime_image.ui.screen.detail.WaifuDetailImageScreen

fun NavGraphBuilder.waifuImageDetailScreen(
    navController: NavHostController
) {
    composable(
        route = Destination.WaifuImageDetail.route,
        arguments = listOf(
            navArgument("image_json") {
                type = WaifuImageNavType()
            }
        ),
        enterTransition = {
            slideIntoContainer(towards = AnimatedContentTransitionScope.SlideDirection.Up)
        },
        exitTransition = {
            slideOutOfContainer(towards = AnimatedContentTransitionScope.SlideDirection.Down)
        }
    ) { navBackStackEntry ->
        val context = LocalContext.current
        val imageJson: WaifuImage? =
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                navBackStackEntry.arguments?.getParcelable(
                    "image_json",
                    WaifuImage::class.java
                )
            } else {
                navBackStackEntry.arguments?.getParcelable("image_json")
            }

        val vm = hiltViewModel<WaifuDetailImageViewModel>()
        LaunchedEffect(Unit) {
            imageJson?.let { image ->
                vm.setWaifuImages(image)
            }
        }

        val waifuImageState by vm.waifuImages.collectAsState()
        when (waifuImageState) {
            is DataState.Error -> {
                ErrorScreen(
                    errorMessage = (waifuImageState as DataState.Error).errorMessage,
                    onRetry = { /*TODO*/ }
                )
            }

            is DataState.Success -> {
                WaifuDetailImageScreen(
                    image = (waifuImageState as DataState.Success<WaifuImage>).data,
                    onEvent = { event ->
                        when (event) {
                            WaifuDetailImageEvent.OnBackPressed -> navController.popBackStack()
                            is WaifuDetailImageEvent.OnSaveImage -> {
                                vm.downloadWaifu(event.image)
                            }

                            is WaifuDetailImageEvent.OnShareImage -> {

                                val intent = Intent().apply {
                                    action = Intent.ACTION_SEND
                                    putExtra(Intent.EXTRA_TEXT, event.image.source)
                                    type = "text/plain"
                                }

                                context.startActivity(Intent.createChooser(intent, null))
                            }
                        }
                    })
            }

            else -> Unit
        }
    }
}