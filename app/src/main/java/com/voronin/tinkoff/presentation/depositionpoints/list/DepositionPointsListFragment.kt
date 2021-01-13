package com.voronin.tinkoff.presentation.depositionpoints.list

import android.os.Bundle
import com.voronin.tinkoff.R
import com.voronin.tinkoff.presentation.base.BaseFragment
import kotlinx.android.synthetic.main.fragment_depositions_points_list.depositionPointsListRecyclerView
import kotlinx.android.synthetic.main.fragment_depositions_points_list.depositionPointsListStateViewFlipper
import javax.inject.Inject

class DepositionPointsListFragment : BaseFragment(R.layout.fragment_depositions_points_list) {

    private val viewModel: DepositionPointsListViewModel by appViewModels()

    @Inject lateinit var depositionsPointAdapter: DepositionsPointAdapter

    override fun callOperations() {
        viewModel.getPoints()
    }

    override fun onSetupLayout(savedInstanceState: Bundle?) {
        depositionPointsListRecyclerView.adapter = depositionsPointAdapter
    }

    override fun onBindViewModel() = with(viewModel) {
        markersLiveData.observe { points ->
            depositionsPointAdapter.submitList(points)
        }
        markersProgress.observe {
            depositionPointsListStateViewFlipper.changeState(it)
        }
    }
}