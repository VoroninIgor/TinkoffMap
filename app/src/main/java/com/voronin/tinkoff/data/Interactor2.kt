package com.voronin.tinkoff.data

import androidx.lifecycle.MutableLiveData
import com.voronin.tinkoff.utils.async
import io.reactivex.Flowable
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.disposables.Disposable

abstract class Interactor2<T> constructor(
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

class SingleInteractor2<T>(
    private val interaction: Single<T>,
    data: MutableLiveData<T>,
    state: MutableLiveData<OperationState>? = null
) : Interactor2<T>(data, state) {

    override fun execute(): Disposable {
        disposable = interaction.async()
            .doOnSubscribe { onSubscribe.invoke() }
            .doOnDispose { onDispose.invoke() }
            .subscribe(onNext, onError)
        return disposable as Disposable
    }
}

class ObservableInteractor2<T>(
    private val interaction: Observable<T>,
    data: MutableLiveData<T>,
    state: MutableLiveData<OperationState>
) : Interactor2<T>(data, state) {

    override fun execute(): Disposable {
        disposable = interaction.async()
            // to rx search (subscribe != request)
//                .doOnSubscribe { onSubscribe.invoke() }
            .doOnDispose { onDispose.invoke() }
            .subscribe(onNext, onError)
        return disposable as Disposable
    }
}

class FlowableInteractor2<T>(
    private val interaction: Flowable<T>,
    data: MutableLiveData<T>,
    state: MutableLiveData<OperationState>? = null
) : Interactor2<T>(data, state) {

    override fun execute(): Disposable {
        disposable = interaction.async()
            .doOnSubscribe { onSubscribe.invoke() }
            .doOnCancel { onDispose.invoke() }
            .subscribe(onNext, onError)
        return disposable as Disposable
    }
}
