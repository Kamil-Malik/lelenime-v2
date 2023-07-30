package com.lelestacia.lelenimev2.feature.anime_image.ui.screen.option

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Save
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.lelestacia.lelenimev2.core.common.button.LelenimeBottomSheetButton
import com.lelestacia.lelenimev2.core.theme.LelenimeV2Theme
import com.lelestacia.lelenimev2.feature.anime_image.domain.model.model.WaifuImage
import com.lelestacia.lelenimev2.feature.anime_image.domain.model.model.WaifuImageVariantOne

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WaifuImageOptionScreen(
    image: WaifuImage,
    onEvent: (WaifuImageOptionEvent) -> Unit,
    modifier: Modifier = Modifier
) {
    Surface(
        modifier = modifier.fillMaxSize()
    ) {
        Card(
            shape = RoundedCornerShape(topStartPercent = 5, topEndPercent = 5),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.surfaceVariant
            ),
        ) {
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .padding(vertical = 16.dp)
                    .fillMaxWidth()
            ) {
                Box(
                    modifier = Modifier
                        .height(4.dp)
                        .width(24.dp)
                        .clip(RoundedCornerShape(percent = 100))
                        .background(MaterialTheme.colorScheme.onSurfaceVariant)
                )
            }
            Spacer(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(8.dp)
            )
            LelenimeBottomSheetButton(
                icon = Icons.Default.Info,
                iconDescription = null,
                text = "Detail Image",
                onClick = {
                    onEvent(WaifuImageOptionEvent.OnDetail(image))
                }
            )
            LelenimeBottomSheetButton(
                icon = Icons.Default.Save,
                iconDescription = "Save Image",
                text = "Save Image",
                onClick = {
                    onEvent(WaifuImageOptionEvent.OnSave(image))
                }
            )
            LelenimeBottomSheetButton(
                icon = Icons.Default.Share,
                iconDescription = "Share Image",
                text = "Share",
                onClick = {
                    onEvent(WaifuImageOptionEvent.OnShare(image.source))
                }
            )
            LelenimeBottomSheetButton(
                icon = Icons.Default.Close,
                iconDescription = "Close Options",
                text = "Close",
                onClick = {
                    onEvent(WaifuImageOptionEvent.OnClose)
                }
            )
            Spacer(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(8.dp)
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
fun PreviewWaifuImageOptionScreen() {
    LelenimeV2Theme(
        dynamicColor = false
    ) {
        WaifuImageOptionScreen(
            image = WaifuImageVariantOne,
            onEvent = {}
        )
    }
}