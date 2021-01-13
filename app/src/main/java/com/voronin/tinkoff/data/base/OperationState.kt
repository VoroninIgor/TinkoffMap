package com.voronin.tinkoff.data.base

sealed class OperationState {

    class Loading : OperationState()
    class Success : OperationState()
    class Cancel : OperationState()
    class Error(val e: Throwable) : OperationState()

    companion object {
        fun loading(): OperationState =
            Loading()

        fun cancel(): OperationState =
            Cancel()

        fun success(): OperationState =
            Success()

        fun error(e: Throwable): OperationState =
            Error(e)
    }

//    fun getDataIfSuccess(): T? {
//        return if (this is Success) this.data else null
//    }
}
