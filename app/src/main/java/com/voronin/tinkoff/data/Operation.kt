package com.voronin.tinkoff.data

sealed class Operation<T> {

    class Loading<T> : Operation<T>()
    class Cancel<T> : Operation<T>()
    data class Success<T>(val data: T) : Operation<T>()
    data class Error<T>(val e: Throwable) : Operation<T>()

    companion object {
        fun <T> loading(): Operation<T> =
            Loading()

        fun <T> cancel(): Operation<T> =
            Cancel()

        fun <T> success(data: T): Operation<T> =
            Success(data)

        fun <T> error(e: Throwable): Operation<T> =
            Error(e)
    }

    fun getDataIfSuccess(): T? {
        return if (this is Success) this.data else null
    }
}
