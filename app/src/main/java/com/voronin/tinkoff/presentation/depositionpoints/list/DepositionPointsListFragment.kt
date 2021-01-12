package com.voronin.tinkoff.presentation.depositionpoints.list

import android.os.Bundle
import com.voronin.tinkoff.R
import com.voronin.tinkoff.presentation.base.BaseFragment

class DepositionPointsListFragment : BaseFragment(R.layout.fragment_depositions_points_list) {

    private val viewModel: DepositionPointsListViewModel by appViewModels()

    override fun callOperations() {

    }

    override fun onSetupLayout(savedInstanceState: Bundle?) {

    }

    override fun onBindViewModel() = with(viewModel) {

    }
}