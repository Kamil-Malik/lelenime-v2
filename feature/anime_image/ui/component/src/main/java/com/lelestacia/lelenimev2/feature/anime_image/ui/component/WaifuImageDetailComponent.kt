package com.lelestacia.lelenimev2.feature.anime_image.ui.component

import android.content.res.Configuration
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
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

@Composable
fun WaifuImageDetailComponent(
    image: WaifuImage
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
            filterQuality = FilterQuality.High,
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(image.width.toFloat() / image.height.toFloat())
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
fun PreviewWaifuImageDetailComponent() {
    LelenimeV2Theme(
        dynamicColor = false
    ) {
        WaifuImageDetailComponent(
            image = WaifuImageVariantOne
        )
    }
}