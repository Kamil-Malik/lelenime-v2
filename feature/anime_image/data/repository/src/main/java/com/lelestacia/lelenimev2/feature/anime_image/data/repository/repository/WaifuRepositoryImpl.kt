package com.lelestacia.lelenimev2.feature.anime_image.data.repository.repository

import com.lelestacia.lelenimev2.core.utils.DataState
import com.lelestacia.lelenimev2.core.utils.EventHandler
import com.lelestacia.lelenimev2.feature.anime_image.data.remote.waifu_im.endpoint.WaifuIM
import com.lelestacia.lelenimev2.feature.anime_image.data.remote.waifu_im.response.ResponseDto
import com.lelestacia.lelenimev2.feature.anime_image.data.remote.waifu_im.response.WaifuImageDto
import com.lelestacia.lelenimev2.feature.anime_image.data.repository.mapper.asWaifuImage
import com.lelestacia.lelenimev2.feature.anime_image.domain.model.model.WaifuImage
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.onStart
import javax.inject.Inject

class WaifuRepositoryImpl @Inject constructor(
    private val waifuIM: WaifuIM,
    private val eventHandler: EventHandler = EventHandler(),
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO
) : WaifuRepository {

    override fun getWaifus(): Flow<DataState<List<WaifuImage>>> {
        return flow<DataState<List<WaifuImage>>> {
            val apiResponse: ResponseDto = waifuIM.getWaifus()
            val waifusDto: List<WaifuImageDto> = apiResponse.images
            val waifus: List<WaifuImage> = waifusDto.map(WaifuImageDto::asWaifuImage)
            emit(DataState.Success(data = waifus))
        }.onStart {
            emit(DataState.Loading)
        }.catch { t ->
            emit(DataState.Error(errorMessage = eventHandler(t)))
        }.flowOn(ioDispatcher)
    }
}