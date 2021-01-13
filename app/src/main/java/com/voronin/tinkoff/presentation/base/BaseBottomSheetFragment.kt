package com.voronin.tinkoff.presentation.base

import android.app.Activity
import android.content.Context
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.annotation.MainThread
import androidx.fragment.app.Fragment
import androidx.fragment.app.createViewModelLazy
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import dagger.android.support.AndroidSupportInjection

abstract class BaseBottomSheetFragment(@LayoutRes private val layoutRes: Int) : BottomSheetDialogFragment() {

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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        callOperations()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        onSetupLayout(savedInstanceState)
        onBindViewModel()
    }

    override fun onAttach(activity: Activity) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            AndroidSupportInjection.inject(this)
        }
        super.onAttach(activity)
    }

    override fun onAttach(context: Context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            AndroidSupportInjection.inject(this)
        }
        super.onAttach(context)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(layoutRes, container, false)
    }

    protected infix fun <T> LiveData<T>.observe(block: (T) -> Unit) {
        observe(this@BaseBottomSheetFragment.viewLifecycleOwner, { t -> block.invoke(t) })
    }

    @MainThread
    inline fun <reified VM : ViewModel> Fragment.appViewModels() =
        createViewModelLazy(
            VM::class,
            { requireActivity().viewModelStore },
            { requireActivity().defaultViewModelProviderFactory }
        )
}
