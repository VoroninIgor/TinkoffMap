package com.voronin.tinkoff.presentation.depositionpoints.viewer

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import com.voronin.tinkoff.R
import com.voronin.tinkoff.presentation.base.BaseFragment
import com.voronin.tinkoff.presentation.depositionpoints.list.DepositionPointsListFragment
import com.voronin.tinkoff.presentation.depositionpoints.map.DepositionPointsMapFragment
import kotlinx.android.synthetic.main.fragment_depositions_points_viewer.tabsDepositionsPoint
import kotlinx.android.synthetic.main.fragment_depositions_points_viewer.viewPagerDepositionsPoint

class DepositionPointsViewerFragment : BaseFragment(R.layout.fragment_depositions_points_viewer) {

    private val viewModel: DepositionPointsViewerViewModel by appViewModels()

    private val adapter by lazy {
        DepositionPointsPagerAdapter(
            requireContext(),
            childFragmentManager
        )
    }

    override fun callOperations() = Unit

    override fun onSetupLayout(savedInstanceState: Bundle?) {
        viewPagerDepositionsPoint.adapter = adapter
        tabsDepositionsPoint.setupWithViewPager(viewPagerDepositionsPoint)
    }

    override fun onBindViewModel() = with(viewModel) {
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        adapter.onActivityResult(requestCode, resultCode, data)
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

        fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
            list.forEach {
                it.first.onActivityResult(requestCode, resultCode, data)
            }
        }

        override fun getCount(): Int = list.size

        override fun getItem(position: Int): Fragment = list[position].first

        override fun getPageTitle(position: Int): CharSequence {
            return context.getString(list[position].second)
        }
    }
}
