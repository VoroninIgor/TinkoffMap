package com.voronin.tinkoff.ext

import com.voronin.tinkoff.data.base.DEFAULT_ERROR_MESSAGE
import com.voronin.tinkoff.data.base.GeneralError
import com.voronin.tinkoff.data.base.ParsedError
import okhttp3.internal.http2.ErrorCode

fun Throwable.parseError(): ParsedError {
    return when (this) {
//        is StatusException -> {
//            val error = getErrorModel(defaultMessage = DEFAULT_ERROR_MESSAGE)
//
//            val code = mapError(error.code)
//            val message = error.message
//
//            GeneralError(code, message)
//        }
        else -> GeneralError(ErrorCode.INTERNAL_ERROR, DEFAULT_ERROR_MESSAGE)
    }
}

