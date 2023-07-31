package com.lelestacia.lelenimev2.feature.anime_image.ui.screen.detail

import android.Manifest
import android.content.res.Configuration
import android.os.Build
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Save
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.BottomSheetScaffold
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.rememberBottomSheetScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.MultiplePermissionsState
import com.google.accompanist.permissions.PermissionState
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberMultiplePermissionsState
import com.google.accompanist.permissions.rememberPermissionState
import com.lelestacia.lelenimev2.core.theme.LelenimeV2Theme
import com.lelestacia.lelenimev2.feature.anime_image.domain.model.model.WaifuImage
import com.lelestacia.lelenimev2.feature.anime_image.domain.model.model.WaifuImageVariantFour
import com.lelestacia.lelenimev2.feature.anime_image.ui.component.WaifuImageDetailComponent
import kotlinx.coroutines.launch


@OptIn(
    ExperimentalMaterial3Api::class,
    ExperimentalPermissionsApi::class
)
@Composable
fun WaifuDetailImageScreen(
    image: WaifuImage,
    onEvent: (WaifuDetailImageEvent) -> Unit,
    modifier: Modifier = Modifier
) {

    val scope = rememberCoroutineScope()
    val snackBarHostState by remember {
        mutableStateOf(SnackbarHostState())
    }

    val readAndWritePermission: MultiplePermissionsState =
        rememberMultiplePermissionsState(
            permissions = listOf(
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE
            ),
            onPermissionsResult = {
                if (it.containsValue(false)) {
                    scope.launch {
                        snackBarHostState.showSnackbar(
                            message = "Read and Write permission should be granted in order to save image",
                            duration = SnackbarDuration.Long
                        )
                    }
                }
            }
        )

    val notificationPermission: PermissionState =
        rememberPermissionState(
            permission = Manifest.permission.POST_NOTIFICATIONS,
            onPermissionResult = { isGranted ->
                if (!isGranted) {
                    scope.launch {
                        snackBarHostState.showSnackbar(
                            message = "Notification permission should be given in order for notification purposes",
                            duration = SnackbarDuration.Long
                        )
                    }
                }
            })

    Surface {
        BottomSheetScaffold(
            topBar = {
                TopAppBar(
                    navigationIcon = {
                        IconButton(onClick = { onEvent(WaifuDetailImageEvent.OnBackPressed) }) {
                            Icon(
                                imageVector = Icons.Default.ArrowBack,
                                contentDescription = null
                            )
                        }
                    },
                    title = {
                        Text(
                            text = "Post #${image.imageId}",
                            style = MaterialTheme.typography.titleMedium.copy(
                                fontWeight = FontWeight.Black
                            )
                        )
                    },
                    actions = {
                        IconButton(
                            onClick = {
                                if (!readAndWritePermission.allPermissionsGranted) {
                                    readAndWritePermission.launchMultiplePermissionRequest()
                                    return@IconButton
                                }

                                if (Build.VERSION.SDK_INT >= 33) {
                                    if (!notificationPermission.status.isGranted) {
                                        notificationPermission.launchPermissionRequest()
                                        return@IconButton
                                    }
                                }

                                onEvent(WaifuDetailImageEvent.OnSaveImage(image))
                            }) {
                            Icon(
                                imageVector = Icons.Default.Save,
                                contentDescription = null
                            )
                        }
                        IconButton(onClick = { onEvent(WaifuDetailImageEvent.OnShareImage(image)) }) {
                            Icon(
                                imageVector = Icons.Default.Share,
                                contentDescription = null
                            )
                        }
                    }
                )
            },
            sheetContent = {
                /*
                 *  TODO: This content will be for further development
                 */
            },
            sheetSwipeEnabled = false,
            sheetPeekHeight = 0.dp,
            scaffoldState = rememberBottomSheetScaffoldState(),
            modifier = modifier
        ) { paddingValues ->
            WaifuImageDetailComponent(
                image = image,
                modifier = Modifier.padding(paddingValues)
            )
        }
    }
}

@Preview(name = "Preview Waifu Image Day Mode", showBackground = true)
@Preview(
    name = "Preview Waifu Image Dark Mode",
    showBackground = true,
    uiMode = Configuration.UI_MODE_NIGHT_YES
)
@Composable
fun PreviewWaifuDetailImageScreen() {
    LelenimeV2Theme(
        dynamicColor = false
    ) {
        WaifuDetailImageScreen(
            image = WaifuImageVariantFour,
            onEvent = {}
        )
    }
}