package com.lelestacia.lelenimev2.feature.anime_image.ui.screen.image

import android.content.res.Configuration
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridItemSpan
import androidx.compose.foundation.lazy.staggeredgrid.items
import androidx.compose.material3.Button
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.lelestacia.lelenimev2.core.theme.LelenimeV2Theme
import com.lelestacia.lelenimev2.feature.anime_image.domain.model.model.WaifuImage
import com.lelestacia.lelenimev2.feature.anime_image.domain.model.model.WaifuImageVariantFour
import com.lelestacia.lelenimev2.feature.anime_image.domain.model.model.WaifuImageVariantOne
import com.lelestacia.lelenimev2.feature.anime_image.domain.model.model.WaifuImageVariantThree
import com.lelestacia.lelenimev2.feature.anime_image.domain.model.model.WaifuImageVariantTwo
import com.lelestacia.lelenimev2.feature.anime_image.ui.component.WaifuImagePreviewComponent

@Composable
fun WaifuImageScreen(
    images: List<WaifuImage>,
    onRefresh: () -> Unit,
    onClicked: (WaifuImage) -> Unit,
    onLongClicked: (WaifuImage) -> Unit,
    modifier: Modifier = Modifier
) {
    Surface(
        modifier = modifier
    ) {
        LazyVerticalStaggeredGrid(
            columns = StaggeredGridCells.Fixed(2),
            verticalItemSpacing = 4.dp,
            horizontalArrangement = Arrangement.spacedBy(4.dp),
            modifier = Modifier
                .fillMaxSize()
                .padding(4.dp)
        ) {
            items(items = images, key = { it.signature }) { image ->
                WaifuImagePreviewComponent(
                    image = image,
                    onClicked = onClicked,
                    onLongClicked = onLongClicked
                )
            }
            if (images.isNotEmpty()) {
                item(
                    span = StaggeredGridItemSpan.FullLine
                ) {
                    Box(
                        contentAlignment = Alignment.Center,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(24.dp)
                    ) {
                        Column(
                            verticalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            Text(text = "Done with current image?")
                            Button(onClick = onRefresh) {
                                Text(text = "Load Another Images")
                            }
                        }
                    }
                }
            }
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
fun PreviewWaifuImageScreen() {
    LelenimeV2Theme(
        dynamicColor = false
    ) {
        WaifuImageScreen(
            images = listOf(
                WaifuImageVariantOne,
                WaifuImageVariantTwo,
                WaifuImageVariantThree,
                WaifuImageVariantFour,
            ),
            onClicked = {},
            onLongClicked = {},
            onRefresh = {}
        )
    }
}