package com.lelestacia.lelenimev2

import android.net.Uri
import android.os.Build
import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import androidx.work.Constraints
import androidx.work.Data
import androidx.work.NetworkType
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import com.google.accompanist.navigation.material.ExperimentalMaterialNavigationApi
import com.google.accompanist.navigation.material.ModalBottomSheetLayout
import com.google.accompanist.navigation.material.bottomSheet
import com.google.accompanist.navigation.material.rememberBottomSheetNavigator
import com.lelestacia.lelenimev2.core.common.screen.ErrorScreen
import com.lelestacia.lelenimev2.core.common.screen.LoadingScreen
import com.lelestacia.lelenimev2.core.common.worker.ImageDownloadWorker
import com.lelestacia.lelenimev2.core.utils.DataState
import com.lelestacia.lelenimev2.core.utils.Destination
import com.lelestacia.lelenimev2.feature.anime_image.domain.model.model.WaifuImage
import com.lelestacia.lelenimev2.feature.anime_image.domain.model.type.WaifuImageNavType
import com.lelestacia.lelenimev2.feature.anime_image.domain.usecases.viewmodel.WaifuImageOptionsViewModel
import com.lelestacia.lelenimev2.feature.anime_image.domain.usecases.viewmodel.WaifuImageViewModel
import com.lelestacia.lelenimev2.feature.anime_image.ui.screen.detail.WaifuDetailImageEvent
import com.lelestacia.lelenimev2.feature.anime_image.ui.screen.detail.WaifuDetailImageScreen
import com.lelestacia.lelenimev2.feature.anime_image.ui.screen.image.WaifuImageScreen
import com.lelestacia.lelenimev2.feature.anime_image.ui.screen.option.WaifuImageOptionEvent
import com.lelestacia.lelenimev2.feature.anime_image.ui.screen.option.WaifuImageOptionScreen

@OptIn(ExperimentalMaterialNavigationApi::class)
@Composable
fun LelenimeApp() {
    val bottomSheetNavigator = rememberBottomSheetNavigator()
    val navController: NavHostController = rememberNavController(bottomSheetNavigator)
    ModalBottomSheetLayout(bottomSheetNavigator = bottomSheetNavigator) {
        NavHost(
            navController = navController,
            startDestination = Destination.WaifuImageScreen.route
        ) {
            composable(route = Destination.WaifuImageScreen.route) {
                val vm = hiltViewModel<WaifuImageViewModel>()
                val imagesState by vm.waifus.collectAsState()
                when (imagesState) {
                    is DataState.Error -> {
                        ErrorScreen(
                            errorMessage = (imagesState as DataState.Error).errorMessage,
                            onRetry = vm::getWaifus
                        )
                    }

                    DataState.Loading -> {
                        LoadingScreen()
                    }

                    DataState.None -> Unit
                    is DataState.Success -> WaifuImageScreen(
                        images = (imagesState as DataState.Success<List<WaifuImage>>).data,
                        onClicked = { waifuImage ->
                            val imageJson: String = vm.encodeWaifuImageAsJson(waifuImage)
                            val destination: String = Destination
                                .WaifuImageDetail
                                .createRoute(Uri.encode(imageJson))
                            navController.navigate(destination)
                        },
                        onLongClicked = { waifuImage ->
                            val imageJson: String = vm.encodeWaifuImageAsJson(waifuImage)
                            val destination: String = Destination
                                .WaifuImageOptionScreen
                                .createRoute(Uri.encode(imageJson))
                            navController.navigate(destination)
                        }
                    )
                }
            }

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
                val vm = hiltViewModel<WaifuImageOptionsViewModel>()
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
                                        val inputData = Data.Builder()
                                            .putString(ImageDownloadWorker.DOWNLOAD_URL, event.image.url)
                                            .putInt(ImageDownloadWorker.FILE_ID, event.image.imageId)
                                            .putString(ImageDownloadWorker.FILE_NAME, event.image.imageId.toString())
                                            .build()

                                        val constraints = Constraints.Builder()
                                            .setRequiredNetworkType(NetworkType.CONNECTED) // Optional: Add network constraints if required
                                            .build()

                                        val imageDownloadRequest = OneTimeWorkRequestBuilder<ImageDownloadWorker>()
                                            .setInputData(inputData)
                                            .setConstraints(constraints)
                                            .build()

                                        WorkManager.getInstance(context).enqueue(imageDownloadRequest)
                                    }

                                    is WaifuDetailImageEvent.OnShareImage -> {
//                                        context.startActivity(Intent(Intent.ACTION_SEND).apply {
//                                            data = Uri.parse(event.image.source)
//                                        })
                                    }
                                }
                            })
                    }

                    else -> Unit
                }
            }

            bottomSheet(
                route = Destination.WaifuImageOptionScreen.route,
                arguments = listOf(
                    navArgument("image_json") {
                        type = WaifuImageNavType()
                    }
                )
            ) { navBackStackEntry ->
                val imageJson: WaifuImage? =
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                        navBackStackEntry.arguments?.getParcelable(
                            "image_json",
                            WaifuImage::class.java
                        )
                    } else {
                        navBackStackEntry.arguments?.getParcelable("image_json")
                    }
                val vm = hiltViewModel<WaifuImageOptionsViewModel>()
                LaunchedEffect(Unit) {
                    imageJson?.let { image ->
                        vm.setWaifuImages(image)
                    }
                }

                val waifuImageState by vm.waifuImages.collectAsState()

                when (waifuImageState) {
                    is DataState.Error -> navController.popBackStack()
                    is DataState.Success -> WaifuImageOptionScreen(
                        image = (waifuImageState as DataState.Success<WaifuImage>).data,
                        onEvent = { event ->
                            when (event) {
                                WaifuImageOptionEvent.OnClose -> navController.popBackStack()
                                is WaifuImageOptionEvent.OnDetail -> {}
                                is WaifuImageOptionEvent.OnSave -> {}
                                is WaifuImageOptionEvent.OnShare -> {}
                            }
                        }
                    )

                    else -> Unit
                }
            }
        }
    }
}