package com.voronin.tinkoff.presentation.depositionpoints.detail

import android.app.Dialog
import android.os.Bundle
import androidx.core.os.bundleOf
import com.voronin.tinkoff.R
import com.voronin.tinkoff.presentation.base.BaseBottomSheetFragment
import com.voronin.tinkoff.presentation.depositionpoints.models.DepositionPoint
import com.voronin.tinkoff.utils.ext.setExpandAtStart
import kotlinx.android.synthetic.main.fragment_depositions_point.textViewDepositionPointAddressInfo
import kotlinx.android.synthetic.main.fragment_depositions_point.textViewDepositionPointFullAddress
import kotlinx.android.synthetic.main.fragment_depositions_point.textViewDepositionPointPartnerName
import kotlinx.android.synthetic.main.fragment_depositions_point.textViewDepositionPointPhones
import kotlinx.android.synthetic.main.fragment_depositions_point.textViewDepositionPointWorkHours

class DepositionPointFragment private constructor() : BaseBottomSheetFragment(R.layout.fragment_depositions_point) {

    companion object {

        private const val DEPOSITION_POINT_PARAM_KEY = "deposition_point_param"

        fun newInstance(depositionPoint: DepositionPoint): DepositionPointFragment {
            return DepositionPointFragment().apply {
                arguments = bundleOf(DEPOSITION_POINT_PARAM_KEY to depositionPoint)
            }
        }
    }

    private val viewModel: DepositionPointViewModel by appViewModels()

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return super.onCreateDialog(savedInstanceState).setExpandAtStart()
    }

    override fun callOperations() {
        arguments?.get(DEPOSITION_POINT_PARAM_KEY)?.let { point ->
            viewModel.initDepositionPoint(point as DepositionPoint)
            viewModel.checkPointViewed(point)
        }
    }

    override fun onSetupLayout(savedInstanceState: Bundle?) = Unit

    override fun onBindViewModel() = with(viewModel) {
        depositionPointLiveData.observe { point ->
            bindDepositionPoint(point)
        }
    }

    private fun bindDepositionPoint(point: DepositionPoint) {
        textViewDepositionPointPartnerName.text = point.addressInfo
        textViewDepositionPointWorkHours.text = point.workHours
        textViewDepositionPointPhones.text = point.phones
        textViewDepositionPointAddressInfo.text = point.addressInfo
        textViewDepositionPointFullAddress.text = point.fullAddress
    }
}
