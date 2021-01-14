package com.voronin.tinkoff.presentation.depositionpoints.models

import com.voronin.tinkoff.utils.list.Similarable

data class DepositionPoint(

    val partnerName: String,

    val location: LocationGeo,

    val workHours: String,

    val phones: String,

    val addressInfo: String,

    val fullAddress: String,

    val images: ImageInfo? = null,

) : Similarable<DepositionPoint> {

    override fun areItemsTheSame(other: DepositionPoint): Boolean {
        return this.partnerName == other.partnerName
    }

    override fun areContentsTheSame(other: DepositionPoint): Boolean {
        return this == other
    }
}
