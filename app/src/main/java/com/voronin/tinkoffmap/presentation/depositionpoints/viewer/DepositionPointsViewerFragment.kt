package com.voronin.tinkoffmap.presentation.depositionpoints.viewer

import android.os.Bundle
import com.voronin.tinkoffmap.R
import com.voronin.tinkoffmap.presentation.base.BaseFragment

class DepositionPointsViewerFragment : BaseFragment(R.layout.fragment_depositions_points_viewer) {

    private val viewModel: DepositionPointsViewerViewModel by appViewModels()

    override fun callOperations() {

    }

    override fun onSetupLayout(savedInstanceState: Bundle?) {

    }

    override fun onBindViewModel() = with(viewModel) {

    }
}