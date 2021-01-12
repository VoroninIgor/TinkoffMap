package com.voronin.tinkoffmap.data.base

import okhttp3.internal.http2.ErrorCode

sealed class ParsedError(val code: ErrorCode, val message: String)

class NetworkError(code: ErrorCode, message: String) : ParsedError(code, message)
class GeneralError(code: ErrorCode, message: String) : ParsedError(code, message)

lateinit var DEFAULT_ERROR_MESSAGE: String
