package com.voronin.tinkoffmap.presentation.depositionpoints.map

import android.os.Bundle
import com.voronin.tinkoffmap.R
import com.voronin.tinkoffmap.presentation.base.BaseFragment
import com.voronin.tinkoffmap.presentation.depositionpoints.viewer.DepositionPointsViewerViewModel

class DepositionPointsMapFragment : BaseFragment(R.layout.fragment_depositions_points_map) {

    private val viewModel: DepositionPointsMapViewModel by appViewModels()

    override fun callOperations() {

    }

    override fun onSetupLayout(savedInstanceState: Bundle?) {

    }

    override fun onBindViewModel() = with(viewModel) {

    }
}