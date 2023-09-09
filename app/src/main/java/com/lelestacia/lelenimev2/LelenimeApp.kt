package com.lelestacia.lelenimev2

import android.icu.lang.UCharacter.VerticalOrientation
import android.net.Uri
import androidx.compose.animation.Crossfade
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.google.accompanist.navigation.material.ExperimentalMaterialNavigationApi
import com.google.accompanist.navigation.material.ModalBottomSheetLayout
import com.google.accompanist.navigation.material.rememberBottomSheetNavigator
import com.lelestacia.lelenimev2.core.common.screen.ErrorScreen
import com.lelestacia.lelenimev2.core.common.screen.LoadingScreen
import com.lelestacia.lelenimev2.core.utils.DataState
import com.lelestacia.lelenimev2.core.common.Destination
import com.lelestacia.lelenimev2.core.common.drawer.LelenimeV2DrawerSheet
import com.lelestacia.lelenimev2.core.common.topbar.LelenimeV2TopAppBar
import com.lelestacia.lelenimev2.feature.anime_image.domain.model.model.WaifuImage
import com.lelestacia.lelenimev2.feature.anime_image.domain.usecases.viewmodel.WaifuImageViewModel
import com.lelestacia.lelenimev2.feature.anime_image.ui.screen.image.WaifuImageScreen
import com.lelestacia.lelenimev2.navigation.waifuImageDetailComposableRoute
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterialNavigationApi::class, ExperimentalMaterial3Api::class)
@Composable
fun LelenimeApp() {
    val context = LocalContext.current
    val scope = rememberCoroutineScope()
    val bottomSheetNavigator = rememberBottomSheetNavigator()
    val navController: NavHostController = rememberNavController(bottomSheetNavigator)
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)

    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior()

    ModalNavigationDrawer(
        gesturesEnabled = true,
        drawerState = drawerState,
        drawerContent = {
            LelenimeV2DrawerSheet(navController = navController)
        }
    ) {
        Scaffold(
            topBar = {
                LelenimeV2TopAppBar(
                    scrollBehavior = scrollBehavior,
                    onDrawerToggle = {
                        scope.launch {
                            val targetState: DrawerValue =
                                if (drawerState.isOpen) {
                                    DrawerValue.Closed
                                } else {
                                    DrawerValue.Open
                                }

                            drawerState.animateTo(targetState, tween())
                        }
                    },
                    modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection)
                )
            },
        ) { paddingValues ->
            ModalBottomSheetLayout(
                bottomSheetNavigator = bottomSheetNavigator
            ) {
                NavHost(
                    navController = navController,
                    startDestination = Destination.PopularAnime.route
                ) {
                    composable(
                        route = Destination.PopularAnime.route
                    ) {
                        Box(
                            contentAlignment = Alignment.Center,
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(paddingValues)
                        ) {

                            Text(text = "Popular Anime")
                        }
                    }

                    composable(
                        route = Destination.AiringAnime.route
                    ) {
                        Box(
                            contentAlignment = Alignment.Center,
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(paddingValues)
                        ) {

                            Text(text = "Airing Anime")
                        }
                    }

                    composable(
                        route = Destination.UpcomingAnime.route
                    ) {
                        Box(
                            contentAlignment = Alignment.Center,
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(paddingValues)
                        ) {

                            Text(text = "Upcoming Anime")
                        }
                    }

                    composable(
                        route = Destination.WaifuImageScreen.route,
                        exitTransition = {
                            fadeOut(tween())
                        },
                        popEnterTransition = {
                            fadeIn(tween())
                        }) {
                        val vm = hiltViewModel<WaifuImageViewModel>()
                        val imagesState by vm.waifus.collectAsState()
                        when (imagesState) {
                            is DataState.Error -> {
                                ErrorScreen(
                                    errorMessage = (imagesState as DataState.Error).errorMessage.asString(
                                        context
                                    ),
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
                                },
                                onRefresh = vm::getWaifus,
                                modifier = Modifier
                                    .padding(paddingValues)
                                    .nestedScroll(scrollBehavior.nestedScrollConnection)
                            )
                        }
                    }

                    //DetailScreen
                    waifuImageDetailComposableRoute(
                        navController = navController
                    )
                }
            }
        }
    }
}