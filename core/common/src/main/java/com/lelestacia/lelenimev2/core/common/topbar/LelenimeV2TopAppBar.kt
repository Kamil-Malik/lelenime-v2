package com.lelestacia.lelenimev2.core.common.topbar

import android.content.res.Configuration
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import com.lelestacia.lelenimev2.core.common.R
import com.lelestacia.lelenimev2.core.theme.LelenimeV2Theme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LelenimeV2TopAppBar(
    onDrawerToggle: () -> Unit,
    scrollBehavior: TopAppBarScrollBehavior,
    modifier: Modifier = Modifier
) {
    Surface {
        CenterAlignedTopAppBar(
            title = {
                Text(
                    text = stringResource(id = R.string.app_name),
                    style = MaterialTheme.typography.titleMedium.copy(
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.primary
                    )
                )
            },
            navigationIcon = {
                IconButton(onClick = onDrawerToggle) {
                    Icon(
                        imageVector = Icons.Default.Menu,
                        contentDescription = null
                    )
                }
            },
            scrollBehavior = scrollBehavior,
            modifier = modifier
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Preview(
    name = "Preview Lelenime V2 Drawer Sheet Item Day Mode",
    showBackground = true
)
@Preview(
    name = "Preview Lelenime V2 Drawer Sheet Item Dark Mode",
    showBackground = true,
    uiMode = Configuration.UI_MODE_NIGHT_YES
)
@Composable
fun PreviewLelenimeV2TopAppBar() {
    LelenimeV2Theme(
        dynamicColor = false
    ) {
        LelenimeV2TopAppBar(
            onDrawerToggle = {},
            scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior(),
            modifier = Modifier
        )
    }
}