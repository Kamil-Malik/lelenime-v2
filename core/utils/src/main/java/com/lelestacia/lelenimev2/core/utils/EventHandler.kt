package com.lelestacia.lelenimev2.core.utils

import java.io.IOException
import java.net.SocketTimeoutException
import java.net.UnknownHostException

class EventHandler {

    operator fun invoke(t: Throwable): UIText {
        if (t is UnknownHostException || t is SocketTimeoutException || t is IOException) {
            return UIText.ResourceIdString(
                id = com.lelestacia.lelenimev2.core.common.R.string.message_no_internet_connection,
                args = emptyList()
            )
        }

        return UIText.DynamicString(t.message.orEmpty())
    }
}