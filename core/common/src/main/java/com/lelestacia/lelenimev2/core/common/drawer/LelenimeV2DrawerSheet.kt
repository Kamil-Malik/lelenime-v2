package com.lelestacia.lelenimev2.core.common.drawer

import android.content.res.Configuration
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Girl
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.LocalFireDepartment
import androidx.compose.material.icons.filled.MovieFilter
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.Upcoming
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.lelestacia.lelenimev2.core.common.Destination
import com.lelestacia.lelenimev2.core.common.R
import com.lelestacia.lelenimev2.core.theme.LelenimeV2Theme

@Composable
fun LelenimeV2DrawerSheet(
    navController: NavHostController
) {
    Surface {
        val backStackEntry by navController.currentBackStackEntryAsState()
        val route = backStackEntry?.destination?.route

        ModalDrawerSheet(
            drawerContainerColor = MaterialTheme.colorScheme.surfaceVariant,
            drawerShape = RectangleShape
        ) {
            Column(
                modifier = Modifier.padding(
                    vertical = 8.dp,
                    horizontal = 16.dp
                )
            ) {
                Text(
                    text = stringResource(id = R.string.app_name),
                    style = MaterialTheme.typography.headlineSmall.copy(
                        fontWeight = FontWeight.Black,
                        color = MaterialTheme.colorScheme.primary
                    )
                )
                Text(
                    text = stringResource(id = R.string.app_subtitle),
                    style = MaterialTheme.typography.titleSmall.copy(
                        fontWeight = FontWeight.SemiBold
                    )
                )
            }
            Spacer(
                modifier = Modifier
                    .height(16.dp)
            )
            LelenimeV2DrawerSheetItem(
                title = "Popular Anime",
                icon = Icons.Default.LocalFireDepartment,
                isSelected = route == Destination.PopularAnime.route,
                onClicked = {
                    navController.navigate(Destination.PopularAnime.route) {
                        popUpTo(navController.graph.startDestinationId) {
                            saveState = true
                        }
                        restoreState = true
                        launchSingleTop = true
                    }
                }
            )
            Divider()
            LelenimeV2DrawerSheetItem(
                title = "Airing Anime",
                icon = Icons.Default.MovieFilter,
                isSelected = route == Destination.AiringAnime.route,
                onClicked = {
                    navController.navigate(Destination.AiringAnime.route) {
                        popUpTo(navController.graph.startDestinationId) {
                            saveState = true
                        }
                        restoreState = true
                        launchSingleTop = true
                    }
                }
            )
            Divider()
            LelenimeV2DrawerSheetItem(
                title = "Upcoming Anime",
                icon = Icons.Default.Upcoming,
                isSelected = route == Destination.UpcomingAnime.route,
                onClicked = {
                    navController.navigate(Destination.UpcomingAnime.route) {
                        popUpTo(navController.graph.startDestinationId) {
                            saveState = true
                        }
                        restoreState = true
                        launchSingleTop = true
                    }
                }
            )
            Divider()
            LelenimeV2DrawerSheetItem(
                title = "Waifu",
                icon = Icons.Default.Girl,
                isSelected = route == Destination.WaifuImageScreen.route,
                onClicked = {
                    navController.navigate(Destination.WaifuImageScreen.route) {
                        popUpTo(navController.graph.startDestinationId) {
                            saveState = true
                        }
                        restoreState = true
                        launchSingleTop = true
                    }
                }
            )
            Divider()
            LelenimeV2DrawerSheetItem(
                title = "Settings",
                icon = Icons.Default.Settings,
                isSelected = route == Destination.Setting.route,
                onClicked = {
                    navController.navigate(Destination.Setting.route) {
                        popUpTo(navController.graph.startDestinationId) {
                            saveState = true
                        }
                        restoreState = true
                        launchSingleTop = true
                    }
                }
            )
            Divider()
            LelenimeV2DrawerSheetItem(
                title = "Info",
                icon = Icons.Default.Info,
                isSelected = route == Destination.Info.route,
                onClicked = {
                    navController.navigate(Destination.Setting.route) {
                        popUpTo(navController.graph.startDestinationId) {
                            saveState = true
                        }
                        restoreState = true
                        launchSingleTop = true
                    }
                }
            )
            Divider()
        }
    }
}

@Preview(
    name = "Preview Lelenime V2 Drawer Sheet Day Mode",
    showBackground = true
)
@Preview(
    name = "Preview Lelenime V2 Drawer Sheet Dark Mode",
    showBackground = true,
    uiMode = Configuration.UI_MODE_NIGHT_YES
)
@Composable
fun PreviewLelenimeV2DrawerSheet() {
    LelenimeV2Theme(
        dynamicColor = false
    ) {
        var isSelected by remember {
            mutableStateOf(false)
        }
        LelenimeV2DrawerSheetItem(
            title = "Popular Anime",
            icon = Icons.Default.LocalFireDepartment,
            isSelected = isSelected,
            onClicked = {
                isSelected = !isSelected
            })
    }
}