package com.voronin.tinkoffmap.presentation.depositionpoints.detail

import android.os.Bundle
import com.voronin.tinkoffmap.R
import com.voronin.tinkoffmap.presentation.base.BaseFragment
import com.voronin.tinkoffmap.presentation.depositionpoints.map.DepositionPointsMapViewModel

class DepositionPointFragment : BaseFragment(R.layout.fragment_depositions_point) {

    private val viewModel: DepositionPointViewModel by appViewModels()

    override fun callOperations() {

    }

    override fun onSetupLayout(savedInstanceState: Bundle?) {

    }

    override fun onBindViewModel() = with(viewModel) {

    }
}