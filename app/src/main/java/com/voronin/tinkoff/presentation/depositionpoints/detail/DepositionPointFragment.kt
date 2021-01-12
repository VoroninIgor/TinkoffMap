package com.voronin.tinkoff.presentation.depositionpoints.detail

import android.os.Bundle
import com.voronin.tinkoff.R
import com.voronin.tinkoff.presentation.base.BaseFragment

class DepositionPointFragment : BaseFragment(R.layout.fragment_depositions_point) {

    private val viewModel: DepositionPointViewModel by appViewModels()

    override fun callOperations() {

    }

    override fun onSetupLayout(savedInstanceState: Bundle?) {

    }

    override fun onBindViewModel() = with(viewModel) {

    }
}