package com.lelestacia.lelenimev2.feature.anime_image.domain.usecases.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lelestacia.lelenimev2.core.utils.DataState
import com.lelestacia.lelenimev2.core.utils.UIText
import com.lelestacia.lelenimev2.feature.anime_image.domain.model.model.WaifuImage
import com.lelestacia.lelenimev2.feature.anime_image.domain.usecases.usecases.WaifuUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WaifuDetailImageViewModel @Inject constructor(
    private val useCases: WaifuUseCases
) : ViewModel() {

    private val _waifuImages: MutableStateFlow<DataState<WaifuImage>> =
        MutableStateFlow(DataState.None)
    val waifuImages: StateFlow<DataState<WaifuImage>> =
        _waifuImages.asStateFlow()

    fun decodeWaifuImageJson(image: String) = viewModelScope.launch {
        try {
            _waifuImages.update { DataState.Success(data = useCases.decodeJson(image)) }
        } catch (e: Exception) {
            _waifuImages.update { DataState.Error(errorMessage = UIText.DynamicString(e.message.orEmpty())) }
        }
    }

    fun setWaifuImages(image: WaifuImage) = viewModelScope.launch {
        _waifuImages.update { DataState.Success(data = image) }
    }

    fun downloadWaifu(image: WaifuImage) = viewModelScope.launch {
        useCases.downloadWaifus(image)
    }
}