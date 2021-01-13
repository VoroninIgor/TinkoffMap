package com.voronin.tinkoff.presentation.base

import androidx.lifecycle.ViewModel
import com.voronin.tinkoff.data.base.Interactor
import com.voronin.tinkoff.data.base.Interactor2
import com.voronin.tinkoff.utils.async
import io.reactivex.Completable
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

abstract class BaseViewModel : ViewModel() {

    protected val compositeDisposable = CompositeDisposable()

    fun <T> execute(interactor: Interactor<T>?): Disposable? {
        val disposable = interactor?.execute()
        disposable?.let { compositeDisposable.add(it) }
        return disposable
    }

    fun <T> execute(interactor: Interactor2<T>?): Disposable? {
        val disposable = interactor?.execute()
        disposable?.let { compositeDisposable.add(it) }
        return disposable
    }

    fun execute(
        completable: Completable,
        onComplete: () -> Unit,
        onError: ((error: Throwable) -> Unit)? = null,
    ): Disposable? {
        val disposable = completable.async()
            .subscribe(onComplete, { onError?.invoke(it) })
        disposable.let { compositeDisposable.add(it) }
        return disposable
    }

    override fun onCleared() {
        compositeDisposable.dispose()
        super.onCleared()
    }
}
