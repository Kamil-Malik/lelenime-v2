package com.lelestacia.lelenimev2.feature.anime_image.ui.screen.option

import com.lelestacia.lelenimev2.feature.anime_image.domain.model.model.WaifuImage

sealed class WaifuImageOptionEvent {
    data object OnClose : WaifuImageOptionEvent()
    data class OnSave(val image: WaifuImage) : WaifuImageOptionEvent()
    data class OnShare(val url: String) : WaifuImageOptionEvent()
    data class OnDetail(val image: WaifuImage) : WaifuImageOptionEvent()
}