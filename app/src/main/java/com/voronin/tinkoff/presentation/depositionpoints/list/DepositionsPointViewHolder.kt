package com.voronin.tinkoff.presentation.depositionpoints.list

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.voronin.tinkoff.R
import com.voronin.tinkoff.presentation.depositionpoints.models.DepositionPoint
import com.voronin.tinkoff.utils.ext.inflate
import kotlinx.android.synthetic.main.item_depositions_point.view.depositionsPointsAddressInfo
import kotlinx.android.synthetic.main.item_depositions_point.view.depositionsPointsFullAddress
import kotlinx.android.synthetic.main.item_depositions_point.view.depositionsPointsPartnerName
import kotlinx.android.synthetic.main.item_depositions_point.view.depositionsPointsPhones
import kotlinx.android.synthetic.main.item_depositions_point.view.depositionsPointsWorkHours

class DepositionsPointViewHolder(
    parent: ViewGroup,
) : RecyclerView.ViewHolder(parent.inflate(R.layout.item_depositions_point)) {

    fun bind(item: DepositionPoint) = with(itemView) {
        depositionsPointsPartnerName.text = item.addressInfo
        depositionsPointsWorkHours.text = item.workHours
        depositionsPointsPhones.text = item.phones
        depositionsPointsAddressInfo.text = item.addressInfo
        depositionsPointsFullAddress.text = item.fullAddress
    }
}
