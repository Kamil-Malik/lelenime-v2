package com.lelestacia.lelenimev2.core.utils

import android.content.Context
import androidx.annotation.StringRes
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource

sealed class UIText {
    data class DynamicString(val value: String) : UIText()

    data class ResourceIdString(@StringRes val id: Int, val args: List<Any>) : UIText()

    fun asString(context: Context): String {
        return when (this) {
            is DynamicString -> value
            is ResourceIdString -> context.getString(id, *args.toTypedArray())
        }
    }

    @Composable
    fun asString(): String {
        return when (this) {
            is DynamicString -> value
            is ResourceIdString -> stringResource(id, *args.toTypedArray())
        }
    }
}