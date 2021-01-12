package com.voronin.tinkoffmap.presentation.depositionpoints.list

import android.os.Bundle
import com.voronin.tinkoffmap.R
import com.voronin.tinkoffmap.presentation.base.BaseFragment

class DepositionPointsListFragment : BaseFragment(R.layout.fragment_depositions_points_list) {

    private val viewModel: DepositionPointsListViewModel by appViewModels()

    override fun callOperations() {

    }

    override fun onSetupLayout(savedInstanceState: Bundle?) {

    }

    override fun onBindViewModel() = with(viewModel) {

    }
}