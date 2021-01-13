package com.voronin.tinkoff.presentation.base

import android.app.Activity
import android.content.Context
import android.os.Build
import androidx.annotation.MainThread
import androidx.fragment.app.Fragment
import androidx.fragment.app.createViewModelLazy
import androidx.lifecycle.ViewModel
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import dagger.android.support.AndroidSupportInjection

abstract class BaseBottomSheetFragment : BottomSheetDialogFragment() {

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

    @MainThread
    inline fun <reified VM : ViewModel> Fragment.appViewModels() =
        createViewModelLazy(
            VM::class,
            { requireActivity().viewModelStore },
            { requireActivity().defaultViewModelProviderFactory }
        )
}
