package com.voronin.tinkoff.presentation.base

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.voronin.api.base.LoadableResult
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch

abstract class BaseViewModel : ViewModel() {

    protected fun <T> loadData(
        block: suspend () -> T,
    ): Flow<LoadableResult<T>> = flow {
        try {
            emit(LoadableResult.loading<T>())
            emit(LoadableResult.success(block()))
        } catch (error: Throwable) {
            emit(LoadableResult.failure<T>(error))
        }
    }

    protected fun <T> loadData(
        block: Flow<T>,
    ): Flow<LoadableResult<T>> = flow {
        try {
            emit(LoadableResult.loading())
            block.collect {
                emit(LoadableResult.success(it))
            }
        } catch (error: Throwable) {
            emit(LoadableResult.failure<T>(error))
        }
    }

    protected fun <T> MutableLiveData<LoadableResult<T>>.launchLoadData(
        block: suspend () -> T,
    ): Job = viewModelScope.launch {
        loadData(block).collect { result -> this@launchLoadData.postValue(result) }
    }

    protected fun <T> MutableLiveData<LoadableResult<T>>.launchLoadData(
        block: Flow<T>,
    ): Job = viewModelScope.launch {
        loadData(block).collect { result -> this@launchLoadData.postValue(result) }
    }

}
