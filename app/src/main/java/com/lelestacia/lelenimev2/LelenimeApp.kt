package com.lelestacia.lelenimev2

import android.net.Uri
import android.os.Build
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.google.accompanist.navigation.material.ExperimentalMaterialNavigationApi
import com.google.accompanist.navigation.material.ModalBottomSheetLayout
import com.google.accompanist.navigation.material.bottomSheet
import com.google.accompanist.navigation.material.rememberBottomSheetNavigator
import com.lelestacia.lelenimev2.core.common.screen.ErrorScreen
import com.lelestacia.lelenimev2.core.common.screen.LoadingScreen
import com.lelestacia.lelenimev2.core.utils.DataState
import com.lelestacia.lelenimev2.core.utils.Destination
import com.lelestacia.lelenimev2.feature.anime_image.domain.model.model.WaifuImage
import com.lelestacia.lelenimev2.feature.anime_image.domain.model.type.WaifuImageNavType
import com.lelestacia.lelenimev2.feature.anime_image.domain.usecases.viewmodel.WaifuImageOptionsViewModel
import com.lelestacia.lelenimev2.feature.anime_image.domain.usecases.viewmodel.WaifuImageViewModel
import com.lelestacia.lelenimev2.feature.anime_image.ui.screen.image.WaifuImageScreen
import com.lelestacia.lelenimev2.feature.anime_image.ui.screen.option.WaifuImageOptionEvent
import com.lelestacia.lelenimev2.feature.anime_image.ui.screen.option.WaifuImageOptionScreen
import com.lelestacia.lelenimev2.navigation.waifuImageDetailScreen

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

            //DetailScreen
            waifuImageDetailScreen(
                navController = navController
            )

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