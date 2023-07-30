package com.lelestacia.lelenimev2.feature.anime_image.ui.component

import android.content.res.Configuration
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.FilterQuality
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.tooling.preview.Preview
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.lelestacia.lelenimev2.core.theme.LelenimeV2Theme
import com.lelestacia.lelenimev2.feature.anime_image.domain.model.model.WaifuImage
import com.lelestacia.lelenimev2.feature.anime_image.domain.model.model.WaifuImageVariantOne

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun WaifuImagePreviewComponent(
    image: WaifuImage,
    onClicked: (WaifuImage) -> Unit,
    onLongClicked: (WaifuImage) -> Unit
) {
    Surface {
        val context = LocalContext.current
        val lifecycle = LocalLifecycleOwner.current
        AsyncImage(
            model = ImageRequest
                .Builder(context)
                .data(image.url)
                .lifecycle(lifecycle)
                .build(),
            contentDescription = null,
            filterQuality = FilterQuality.Low,
            modifier = Modifier
                .fillMaxWidth()
                .background(MaterialTheme.colorScheme.surfaceVariant)
                .aspectRatio(image.width.toFloat() / image.height.toFloat())
                .combinedClickable(
                    onClick = {
                        onClicked(image)
                    },
                    onLongClick = {
                        onLongClicked(image)
                    }
                )
        )
    }
}

@Preview(
    name = "Waifu Image Component Day Mode",
    showBackground = true
)
@Preview(
    name = "Waifu Image Component Dark Mode",
    showBackground = true,
    uiMode = Configuration.UI_MODE_NIGHT_YES
)
@Composable
fun PreviewWaifuImagePreviewComponent() {
    LelenimeV2Theme(
        dynamicColor = false
    ) {
        WaifuImagePreviewComponent(
            image = WaifuImageVariantOne,
            onClicked = {},
            onLongClicked = {}
        )
    }
}