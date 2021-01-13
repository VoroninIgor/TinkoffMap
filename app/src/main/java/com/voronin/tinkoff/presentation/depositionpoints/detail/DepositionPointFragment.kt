package com.voronin.tinkoff.presentation.depositionpoints.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.voronin.tinkoff.R
import com.voronin.tinkoff.presentation.base.BaseBottomSheetFragment
import com.voronin.tinkoff.presentation.depositionpoints.models.DepositionPoint

class DepositionPointFragment private constructor() : BaseBottomSheetFragment() {

    companion object {
        fun newInstance(depositionPoint: DepositionPoint): DepositionPointFragment {
            return DepositionPointFragment()
        }
    }

    private val viewModel: DepositionPointViewModel by appViewModels()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_depositions_point, container, false)
    }
}