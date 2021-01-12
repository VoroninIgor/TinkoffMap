package com.voronin.tinkoff.utils

import io.reactivex.Completable
import io.reactivex.CompletableTransformer
import io.reactivex.Flowable
import io.reactivex.FlowableTransformer
import io.reactivex.Observable
import io.reactivex.ObservableTransformer
import io.reactivex.Single
import io.reactivex.SingleTransformer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

fun <T> asyncSingle(): SingleTransformer<T, T> {
    return SingleTransformer {
        it.subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }
}

fun asyncCompletable(): CompletableTransformer {
    return CompletableTransformer {
        it.subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }
}

fun <T> asyncFlowable(): FlowableTransformer<T, T> {
    return FlowableTransformer {
        it.subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }
}

fun <T> asyncObservable(): ObservableTransformer<T, T> {
    return ObservableTransformer {
        it.subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }
}

fun <T> Flowable<T>.async(): Flowable<T> = compose(asyncFlowable())

fun <T> Single<T>.async(): Single<T> = compose(asyncSingle())

fun Completable.async(): Completable = compose(asyncCompletable())

fun <T> Observable<T>.async(): Observable<T> = compose(asyncObservable())
