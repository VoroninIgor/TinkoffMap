package com.voronin.tinkoff.data

import androidx.lifecycle.MutableLiveData
import com.voronin.tinkoff.utils.async
import io.reactivex.Flowable
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.disposables.Disposable

abstract class Interactor<T> constructor(val operation: MutableLiveData<Operation<T>>) {

    protected var disposable: Disposable? = null

    protected val onSubscribe: () -> Unit = {
        operation.value =
            Operation.loading()
    }
    protected val onDispose: () -> Unit = {
        operation.value =
            Operation.cancel()
    }
    protected val onNext: (result: T) -> Unit = {
        operation.value =
            Operation.success(it)
    }
    protected val onError: (error: Throwable) -> Unit = {
        operation.value =
            Operation.error(it)
    }

    abstract fun execute(): Disposable

    fun cancel() {
        if (disposable?.isDisposed != true) {
            disposable?.dispose()
        }
    }
}

class SingleInteractor<T>(private val interaction: Pair<Single<T>, MutableLiveData<Operation<T>>>) :
    Interactor<T>(interaction.second) {

    override fun execute(): Disposable {
        disposable = interaction.first.async()
            .doOnSubscribe { onSubscribe.invoke() }
            .doOnDispose { onDispose.invoke() }
            .subscribe(onNext, onError)
        return disposable as Disposable
    }
}

class ObservableInteractor<T>(private val interaction: Pair<Flowable<T>, MutableLiveData<Operation<T>>>) :
    Interactor<T>(interaction.second) {

    override fun execute(): Disposable {
        disposable = interaction.first.async()
            .doOnSubscribe { onSubscribe.invoke() }
            .doOnCancel { onDispose.invoke() }
            .subscribe(onNext, onError)
        return disposable as Disposable
    }
}

class FlowableInteractor<T>(private val interaction: Pair<Observable<T>, MutableLiveData<Operation<T>>>) :
    Interactor<T>(interaction.second) {

    override fun execute(): Disposable {
        disposable = interaction.first.async()
            .doOnSubscribe { onSubscribe.invoke() }
            .doOnDispose { onDispose.invoke() }
            .subscribe(onNext, onError)
        return disposable as Disposable
    }
}
