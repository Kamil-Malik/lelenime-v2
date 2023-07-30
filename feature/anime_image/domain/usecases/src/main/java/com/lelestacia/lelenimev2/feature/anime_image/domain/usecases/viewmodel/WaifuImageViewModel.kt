package com.lelestacia.lelenimev2.feature.anime_image.domain.usecases.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lelestacia.lelenimev2.core.utils.DataState
import com.lelestacia.lelenimev2.feature.anime_image.domain.model.model.WaifuImage
import com.lelestacia.lelenimev2.feature.anime_image.domain.usecases.usecases.WaifuUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WaifuImageViewModel @Inject constructor(
    private val useCases: WaifuUseCases
) : ViewModel() {

    private val _waifus: MutableStateFlow<DataState<List<WaifuImage>>> =
        MutableStateFlow(DataState.None)
    val waifus: StateFlow<DataState<List<WaifuImage>>> =
        _waifus.asStateFlow()

    fun getWaifus() = viewModelScope.launch {
        useCases.getWaifus().collectLatest { waifuResult ->
            _waifus.update { waifuResult }
        }
    }

    fun encodeWaifuImageAsJson(image: WaifuImage): String {
        return useCases.encodeJson(image)
    }

    init {
        getWaifus()
    }
}