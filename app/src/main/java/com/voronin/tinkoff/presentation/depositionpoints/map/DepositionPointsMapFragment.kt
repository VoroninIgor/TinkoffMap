package com.voronin.tinkoff.presentation.depositionpoints.map

import android.os.Bundle
import com.voronin.tinkoff.R
import com.voronin.tinkoff.presentation.base.BaseFragment

class DepositionPointsMapFragment : BaseFragment(R.layout.fragment_depositions_points_map) {

    private val viewModel: DepositionPointsMapViewModel by appViewModels()

    override fun callOperations() {

    }

    override fun onSetupLayout(savedInstanceState: Bundle?) {

    }

    override fun onBindViewModel() = with(viewModel) {

    }
}