package com.voronin.tinkoffmap.presentation.base

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.annotation.LayoutRes
import androidx.annotation.MainThread
import androidx.fragment.app.Fragment
import androidx.fragment.app.createViewModelLazy
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import dagger.android.support.AndroidSupportInjection
import javax.inject.Inject

abstract class BaseFragment(@LayoutRes layout: Int) : Fragment(layout) {

    @Inject lateinit var viewModelFactory: ViewModelProvider.Factory

    override fun onAttach(context: Context) {
        AndroidSupportInjection.inject(this)
        super.onAttach(context)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        callOperations()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        onSetupLayout(savedInstanceState)
        onBindViewModel()
    }

    /**
     * Вызывать методы ViewModel, которые получают данные из репозиториев
     */
    abstract fun callOperations()

    /**
     * В этом методе производить все настройки View
     * установка OnClickListener, RecyclerView.Adapter/LayoutManager, etc
     */
    abstract fun onSetupLayout(savedInstanceState: Bundle?)

    /**
     * В этом методе производить подписки на изменения значений в LiveData у ViewModel
     */
    abstract fun onBindViewModel()

    protected infix fun <T> LiveData<T>.observe(block: (T) -> Unit) {
        observe(this@BaseFragment.viewLifecycleOwner, { t -> block.invoke(t) })
    }

    @MainThread
    inline fun <reified VM : ViewModel> Fragment.appViewModels() =
        createViewModelLazy(VM::class, { this.viewModelStore }, { viewModelFactory })

}
