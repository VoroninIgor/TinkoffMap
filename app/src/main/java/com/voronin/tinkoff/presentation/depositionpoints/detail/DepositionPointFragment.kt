package com.voronin.tinkoff.presentation.depositionpoints.detail

import android.app.Dialog
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.core.os.bundleOf
import androidx.core.view.isVisible
import com.voronin.tinkoff.R
import com.voronin.tinkoff.presentation.base.BaseBottomSheetFragment
import com.voronin.tinkoff.presentation.depositionpoints.models.DepositionPoint
import com.voronin.tinkoff.utils.ext.loadUrl
import com.voronin.tinkoff.utils.ext.makePhoneAutoLink
import com.voronin.tinkoff.utils.ext.setExpandAtStart
import kotlinx.android.synthetic.main.fragment_depositions_point.buttonDepositionPointGoToMap
import kotlinx.android.synthetic.main.fragment_depositions_point.imageViewDepositionPoint
import kotlinx.android.synthetic.main.fragment_depositions_point.textViewDepositionPointAddressInfo
import kotlinx.android.synthetic.main.fragment_depositions_point.textViewDepositionPointFullAddress
import kotlinx.android.synthetic.main.fragment_depositions_point.textViewDepositionPointPartnerName
import kotlinx.android.synthetic.main.fragment_depositions_point.textViewDepositionPointPhones
import kotlinx.android.synthetic.main.fragment_depositions_point.textViewDepositionPointWorkHours

class DepositionPointFragment private constructor() : BaseBottomSheetFragment(R.layout.fragment_depositions_point) {

    companion object {

        private const val DEPOSITION_POINT_PARAM_KEY = "deposition_point_param"

        fun newInstance(depositionPoint: DepositionPoint, onActionClose: () -> Unit = {}): DepositionPointFragment {
            return DepositionPointFragment().apply {
                this.onActionClose = onActionClose
                arguments = bundleOf(DEPOSITION_POINT_PARAM_KEY to depositionPoint)
            }
        }
    }

    private var onActionClose: () -> Unit = {}

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
        imageViewDepositionPoint.loadUrl(point.images.mediumImageUrl, R.drawable.ic_img_placeholder)

        textViewDepositionPointPartnerName.text = point.partnerName
        textViewDepositionPointPartnerName.isVisible = point.partnerName.isNotEmpty()

        textViewDepositionPointWorkHours.text = point.workHours
        textViewDepositionPointWorkHours.isVisible = point.workHours.isNotEmpty()

        textViewDepositionPointPhones.text = point.phones
        textViewDepositionPointPhones.isVisible = point.phones.isNotEmpty()
        textViewDepositionPointPhones.makePhoneAutoLink()

        textViewDepositionPointAddressInfo.text = point.addressInfo
        textViewDepositionPointAddressInfo.isVisible = point.addressInfo.isNotEmpty()

        textViewDepositionPointFullAddress.text = point.fullAddress
        textViewDepositionPointFullAddress.isVisible = point.fullAddress.isNotEmpty()

        buttonDepositionPointGoToMap.setOnClickListener { openMap(point) }
    }

    private fun openMap(point: DepositionPoint) {
        val gmmIntentUri: Uri = Uri.parse("geo:${point.location.latitude},${point.location.longitude}")
        val mapIntent = Intent(Intent.ACTION_VIEW, gmmIntentUri)
        mapIntent.setPackage("com.google.android.apps.maps")
        if (mapIntent.resolveActivity(requireContext().packageManager) != null) {
            startActivity(mapIntent)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        onActionClose.invoke()
    }
}
