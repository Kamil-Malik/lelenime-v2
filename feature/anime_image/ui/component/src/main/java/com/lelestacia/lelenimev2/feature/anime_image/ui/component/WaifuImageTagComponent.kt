package com.lelestacia.lelenimev2.feature.anime_image.ui.component

import android.content.res.Configuration
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.material3.AssistChip
import androidx.compose.material3.AssistChipDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.PlainTooltipBox
import androidx.compose.material3.PlainTooltipState
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import com.lelestacia.lelenimev2.core.theme.LelenimeV2Theme
import com.lelestacia.lelenimev2.feature.anime_image.domain.model.model.WaifuImageVariantOne
import com.lelestacia.lelenimev2.feature.anime_image.domain.model.model.WaifuTag
import kotlinx.coroutines.launch
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
fun WaifuImageTagComponent(
    tag: WaifuTag,
    modifier: Modifier = Modifier
) {
    val scope = rememberCoroutineScope()
    val tooltipState by remember {
        mutableStateOf(PlainTooltipState())
    }
    Surface {
        PlainTooltipBox(
            tooltip = {
                Text(
                    text = tag.description,
                    style = MaterialTheme.typography.bodyMedium,
                )
            },
            tooltipState = tooltipState
        ) {
            AssistChip(
                onClick = {
                    scope.launch {
                        tooltipState.show()
                    }
                },
                label = {
                    Text(
                        text = tag.name.capitalize(Locale.ROOT),
                        style = MaterialTheme.typography.titleMedium.copy(
                            fontWeight = FontWeight.SemiBold
                        )
                    )
                },
                colors = AssistChipDefaults.elevatedAssistChipColors(
                    containerColor = MaterialTheme.colorScheme.secondary,
                    labelColor = MaterialTheme.colorScheme.onSecondary
                ),
                modifier = modifier
            )
        }
    }
}

@Preview(
    name = "Waifu Image Tag Component Day Mode",
    showBackground = true
)
@Preview(
    name = "Waifu Image Tag Component Dark Mode",
    showBackground = true,
    uiMode = Configuration.UI_MODE_NIGHT_YES
)
@Composable
fun PreviewWaifuImageTagComponent() {
    LelenimeV2Theme(
        dynamicColor = false
    ) {
        WaifuImageTagComponent(
            tag = WaifuImageVariantOne.tags.first()
        )
    }
}