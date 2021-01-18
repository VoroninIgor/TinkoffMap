package com.voronin.tinkoff.data.base

import androidx.lifecycle.MutableLiveData
import com.voronin.tinkoff.utils.async
import io.reactivex.Single
import io.reactivex.disposables.Disposable

abstract class InteractorWithState<T> constructor(
    val data: MutableLiveData<T>,
    private val state: MutableLiveData<OperationState>?
) {

    protected var disposable: Disposable? = null

    protected val onSubscribe: () -> Unit = {
        state?.value = OperationState.loading()
    }
    protected val onDispose: () -> Unit = {
        state?.value = OperationState.cancel()
    }
    protected val onNext: (result: T) -> Unit = {
        state?.value = OperationState.success()
        data.value = it
    }
    protected val onError: (error: Throwable) -> Unit = {
        state?.value = OperationState.error(it)
    }

    abstract fun execute(): Disposable

    fun cancel() {
        if (disposable?.isDisposed != true) {
            disposable?.dispose()
        }
    }
}

class SingleInteractorWithState<T>(
    private val interaction: Single<T>,
    data: MutableLiveData<T>,
    state: MutableLiveData<OperationState>? = null
) : InteractorWithState<T>(data, state) {

    override fun execute(): Disposable {
        disposable = interaction.async()
            .doOnSubscribe { onSubscribe.invoke() }
            .doOnDispose { onDispose.invoke() }
            .subscribe(onNext, onError)
        return disposable as Disposable
    }
}
