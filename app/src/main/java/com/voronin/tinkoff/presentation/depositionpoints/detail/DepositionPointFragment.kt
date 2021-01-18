package com.voronin.tinkoff.presentation.depositionpoints.detail

import android.app.Dialog
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.TextView
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
        imageViewDepositionPoint.loadUrl(
            url = point.images.mediumImageUrl,
            roundedCornerDim = resources.getDimensionPixelSize(R.dimen.default_padding_medium)
        )

        textViewDepositionPointPartnerName.setTextIfNotEmpty(point.partnerName)
        textViewDepositionPointWorkHours.setTextIfNotEmpty(point.workHours)

        textViewDepositionPointPhones.setTextIfNotEmpty(point.phones)
        textViewDepositionPointPhones.makePhoneAutoLink()

        textViewDepositionPointAddressInfo.setTextIfNotEmpty(point.addressInfo)
        textViewDepositionPointFullAddress.setTextIfNotEmpty(point.fullAddress)

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

    private fun TextView.setTextIfNotEmpty(text: String) {
        this.text = text
        this.isVisible = text.isNotEmpty()
    }

    override fun onDestroy() {
        super.onDestroy()
        onActionClose.invoke()
    }
}
