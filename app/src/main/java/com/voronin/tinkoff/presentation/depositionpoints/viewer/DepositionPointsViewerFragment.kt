package com.voronin.tinkoff.presentation.depositionpoints.viewer

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import com.voronin.tinkoff.R
import com.voronin.tinkoff.presentation.base.BaseLocationFragment
import com.voronin.tinkoff.presentation.depositionpoints.list.DepositionPointsListFragment
import com.voronin.tinkoff.presentation.depositionpoints.map.DepositionPointsMapFragment
import kotlinx.android.synthetic.main.fragment_depositions_points_viewer.tabsDepositionsPoint
import kotlinx.android.synthetic.main.fragment_depositions_points_viewer.viewPagerDepositionsPoint

class DepositionPointsViewerFragment : BaseLocationFragment(R.layout.fragment_depositions_points_viewer) {

    private val viewModel: DepositionPointsViewerViewModel by appViewModels()

    private val adapter by lazy {
        DepositionPointsPagerAdapter(
            requireContext(),
            childFragmentManager
        )
    }

    override fun onSuccessLocationListener() {
        lastLocation?.let {
            viewModel.depositionsListViewModel.getPoints(it)
        }
    }

    override fun onLocationEnabled() {
        // TODO("Not yet implemented")
    }

    override fun onLocationDenied() {
        // TODO("Not yet implemented")
    }

    override fun callOperations() = Unit

    override fun onSetupLayout(savedInstanceState: Bundle?) {
        viewPagerDepositionsPoint.adapter = adapter
        tabsDepositionsPoint.setupWithViewPager(viewPagerDepositionsPoint)

        requestLocationPermission()
    }

    override fun onBindViewModel() = with(viewModel) {
    }

    class DepositionPointsPagerAdapter(
        private val context: Context,
        fragmentManager: FragmentManager,
    ) : FragmentStatePagerAdapter(
        fragmentManager,
        BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT
    ) {

        private val list = arrayOf(
            DepositionPointsMapFragment.newInstance() to R.string.map,
            DepositionPointsListFragment.newInstance() to R.string.list
        )

        override fun getCount(): Int = list.size

        override fun getItem(position: Int): Fragment = list[position].first

        override fun getPageTitle(position: Int): CharSequence {
            return context.getString(list[position].second)
        }
    }
}
