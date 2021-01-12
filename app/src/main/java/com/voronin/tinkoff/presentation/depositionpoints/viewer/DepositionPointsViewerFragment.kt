package com.voronin.tinkoff.presentation.depositionpoints.viewer

import android.os.Bundle
import com.voronin.tinkoff.R
import com.voronin.tinkoff.presentation.base.BaseFragment

class DepositionPointsViewerFragment : BaseFragment(R.layout.fragment_depositions_points_viewer) {

    private val viewModel: DepositionPointsViewerViewModel by appViewModels()

    override fun callOperations() {

    }

    override fun onSetupLayout(savedInstanceState: Bundle?) {

    }

    override fun onBindViewModel() = with(viewModel) {

    }
}