package com.voronin.tinkoff.presentation.depositionpoints.list

import android.os.Bundle
import com.voronin.tinkoff.R
import com.voronin.tinkoff.presentation.base.BaseFragment
import com.voronin.tinkoff.presentation.depositionpoints.detail.DepositionPointFragment
import kotlinx.android.synthetic.main.fragment_depositions_points_list.depositionPointsListRecyclerView
import kotlinx.android.synthetic.main.fragment_depositions_points_list.depositionPointsListStateViewFlipper
import javax.inject.Inject

class DepositionPointsListFragment : BaseFragment(R.layout.fragment_depositions_points_list) {

    companion object {
        fun newInstance(): DepositionPointsListFragment {
            return DepositionPointsListFragment()
        }
    }

    private val viewModel: DepositionPointsListViewModel by appViewModels()

    @Inject lateinit var depositionsPointAdapter: DepositionsPointAdapter

    override fun callOperations() = Unit

    override fun onSetupLayout(savedInstanceState: Bundle?) {
        depositionPointsListRecyclerView.adapter = depositionsPointAdapter
        depositionsPointAdapter.onItemClicked = viewModel::onItemSelected
    }

    override fun onBindViewModel() = with(viewModel) {
        depositionsListViewModel.markersLiveData.observe { points ->
            depositionsPointAdapter.submitList(points)
        }
        depositionsListViewModel.markersProgress.observe {
            depositionPointsListStateViewFlipper.changeState(it)
        }
        openDepositionPointDetail.observe { point ->
            DepositionPointFragment.newInstance(point).show(childFragmentManager, "")
        }
    }
}
