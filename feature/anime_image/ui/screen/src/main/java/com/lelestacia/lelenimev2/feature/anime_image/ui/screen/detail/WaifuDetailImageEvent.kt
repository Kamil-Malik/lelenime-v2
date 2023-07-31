package com.lelestacia.lelenimev2.feature.anime_image.ui.screen.detail

import com.lelestacia.lelenimev2.feature.anime_image.domain.model.model.WaifuImage

sealed class WaifuDetailImageEvent {
    data object OnBackPressed : WaifuDetailImageEvent()
    data class OnSaveImage(val image: WaifuImage) : WaifuDetailImageEvent()
    data class OnShareImage(val image: WaifuImage) : WaifuDetailImageEvent()
}