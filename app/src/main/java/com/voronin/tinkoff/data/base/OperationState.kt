package com.voronin.tinkoff.data.base

sealed class OperationState {

    object Loading : OperationState()
    object Success : OperationState()
    object Cancel : OperationState()

    class Error(val e: Throwable) : OperationState()

    companion object {

        fun loading(): OperationState = Loading

        fun cancel(): OperationState = Cancel

        fun success(): OperationState = Success

        fun error(e: Throwable = Throwable()): OperationState = Error(e)
    }
}
