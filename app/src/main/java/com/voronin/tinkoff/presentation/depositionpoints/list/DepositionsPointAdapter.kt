package com.voronin.tinkoff.presentation.depositionpoints.list

import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.voronin.tinkoff.presentation.depositionpoints.models.DepositionPoint
import com.voronin.tinkoff.utils.list.DiffUtilItemCallbackFactory
import javax.inject.Inject

class DepositionsPointAdapter @Inject constructor(
    diffUtilItemCallbackFactory: DiffUtilItemCallbackFactory,
) : ListAdapter<DepositionPoint, DepositionsPointViewHolder>(diffUtilItemCallbackFactory.create()) {

    var onItemClicked: (DepositionPoint) -> Unit = {}

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DepositionsPointViewHolder {
        return DepositionsPointViewHolder(onItemClicked, parent)
    }

    override fun onBindViewHolder(holder: DepositionsPointViewHolder, position: Int) {
        holder.bind(currentList[position])
    }
}