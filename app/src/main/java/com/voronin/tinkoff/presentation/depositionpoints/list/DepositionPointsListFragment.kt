package com.voronin.tinkoff.presentation.depositionpoints.list

import android.os.Bundle
import com.voronin.tinkoff.R
import com.voronin.tinkoff.presentation.base.BaseFragment
import com.voronin.tinkoff.presentation.depositionpoints.detail.DepositionPointFragment
import kotlinx.android.synthetic.main.fragment_depositions_points_list.depositionPointsListRecyclerView
import kotlinx.android.synthetic.main.fragment_depositions_points_list.depositionPointsListStateViewFlipper
import javax.inject.Inject

class DepositionPointsListFragment private constructor() : BaseFragment(R.layout.fragment_depositions_points_list) {

    companion object {
        fun newInstance(): DepositionPointsListFragment {
            return DepositionPointsListFragment()
        }
    }

    private val viewModel: DepositionPointsListViewModel by appViewModels()

    @Inject lateinit var depositionsPointAdapter: DepositionsPointAdapter

    override fun callOperations() = Unit

    override fun onSetupLayout(savedInstanceState: Bundle?) {
        with(depositionPointsListRecyclerView) {
            adapter = depositionsPointAdapter
            itemAnimator = null
        }

        depositionsPointAdapter.onItemClicked = viewModel::onItemSelected
        depositionPointsListStateViewFlipper.setRetryMethod {
            viewModel.depositionsListViewModel.getPoints()
        }
        depositionPointsListStateViewFlipper.setStateEmpty()
    }

    override fun onBindViewModel() = with(viewModel) {
        depositionsListViewModel.markersLiveData.observe { points ->
            depositionsPointAdapter.submitList(points)
            if (points.isEmpty()) {
                depositionPointsListStateViewFlipper.setStateEmpty()
            }
        }
        depositionsListViewModel.markersProgressLiveData.observe {
            depositionPointsListStateViewFlipper.changeState(it)
        }
        openDetailLiveData.observe { point ->
            DepositionPointFragment.newInstance(
                point,
                object : DepositionPointFragment.OnActionClose {
                    override fun onClose() = refreshViewedPoint()
                }
            ).show(childFragmentManager, "")
        }
        viewedListLiveData.observe { viewedList ->
            depositionsPointAdapter.updateViewedItems(viewedList)
        }
    }

    override fun onResume() {
        super.onResume()
        refreshViewedPoint()
    }

    private fun refreshViewedPoint() {
        viewModel.refreshViewedPoint()
    }
}
