package com.lelestacia.lelenimev2.core.utils

sealed class DataState<out T> {
    data class Success<T>(val data: T) : DataState<T>()
    data class Error(val errorMessage: UIText) : DataState<Nothing>()
    data object Loading : DataState<Nothing>()
    data object None : DataState<Nothing>()
}