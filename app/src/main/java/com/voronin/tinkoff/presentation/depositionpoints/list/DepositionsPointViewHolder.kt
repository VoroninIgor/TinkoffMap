package com.voronin.tinkoff.presentation.depositionpoints.list

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.voronin.tinkoff.R
import com.voronin.tinkoff.presentation.depositionpoints.models.DepositionPoint
import com.voronin.tinkoff.utils.ext.inflate
import kotlinx.android.synthetic.main.item_depositions_point.view.textViewDepositionsPointAddressInfo
import kotlinx.android.synthetic.main.item_depositions_point.view.textViewDepositionsPointFullAddress
import kotlinx.android.synthetic.main.item_depositions_point.view.textViewDepositionsPointPartnerName
import kotlinx.android.synthetic.main.item_depositions_point.view.textViewDepositionsPointPhones
import kotlinx.android.synthetic.main.item_depositions_point.view.textViewDepositionsPointWorkHours

class DepositionsPointViewHolder(
    private val onItemSelected: (DepositionPoint) -> Unit,
    parent: ViewGroup,
) : RecyclerView.ViewHolder(parent.inflate(R.layout.item_depositions_point)) {

    fun bind(item: DepositionPoint) = with(itemView) {
        setOnClickListener { onItemSelected.invoke(item) }

        textViewDepositionsPointPartnerName.text = item.addressInfo
        textViewDepositionsPointWorkHours.text = item.workHours
        textViewDepositionsPointPhones.text = item.phones
        textViewDepositionsPointAddressInfo.text = item.addressInfo
        textViewDepositionsPointFullAddress.text = item.fullAddress
    }
}
