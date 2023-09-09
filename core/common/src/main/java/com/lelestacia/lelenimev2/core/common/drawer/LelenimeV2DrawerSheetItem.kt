package com.lelestacia.lelenimev2.core.common.drawer

import android.content.res.Configuration
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LocalFireDepartment
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.lelestacia.lelenimev2.core.common.Destination
import com.lelestacia.lelenimev2.core.theme.LelenimeV2Theme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LelenimeV2DrawerSheetItem(
    title: String,
    icon: ImageVector,
    isSelected: Boolean,
    onClicked: (String) -> Unit
) {
    val backgroundColor by animateColorAsState(
        targetValue =
        if (isSelected) {
            MaterialTheme.colorScheme.primary
        } else {
            MaterialTheme.colorScheme.surfaceVariant
        },
        label = "Color Animation",
        animationSpec = tween(250)
    )
    Surface {
        Card(
            onClick = {
                onClicked(Destination.UpcomingAnime.route)
            },
            shape = RectangleShape,
            colors = CardDefaults.cardColors(
                containerColor = backgroundColor
            ),
            modifier = Modifier.fillMaxWidth()
        ) {
            Row(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(16.dp)
            ) {
                Icon(
                    imageVector = icon,
                    contentDescription = null
                )
                Text(
                    text = title,
                    style = MaterialTheme.typography.titleMedium.copy(
                        fontWeight = FontWeight.Bold
                    )
                )
            }
        }
    }
}

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
fun PreviewLelenimeV2DrawerSheetItem() {
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