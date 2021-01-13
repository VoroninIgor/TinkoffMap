package com.voronin.tinkoff.utils.ext

import android.app.Dialog
import android.os.Handler
import android.os.Looper
import android.view.View
import android.widget.FrameLayout
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog

fun Dialog.setExpandAtStart(
    skipCollapseDialog: Boolean = false,
    isHideableDialog: Boolean = true,
    disableDrag: Boolean = false
): Dialog {
    setOnShowListener { dialog ->
        Handler(Looper.getMainLooper()).post {
            (dialog as BottomSheetDialog).run {
                findViewById<FrameLayout>(com.google.android.material.R.id.design_bottom_sheet)?.let {
                    BottomSheetBehavior.from(it).apply {
                        state = BottomSheetBehavior.STATE_EXPANDED
                        isHideable = isHideableDialog
                        skipCollapsed = skipCollapseDialog

                        if (disableDrag) {
                            addBottomSheetCallback(
                                object : BottomSheetBehavior.BottomSheetCallback() {
                                    override fun onSlide(view: View, p1: Float) = Unit

                                    override fun onStateChanged(view: View, newState: Int) {
                                        if (newState == BottomSheetBehavior.STATE_DRAGGING) {
                                            state = BottomSheetBehavior.STATE_EXPANDED
                                        }
                                    }
                                }
                            )
                        }
                    }
                }
            }
        }
    }
    return this
}
