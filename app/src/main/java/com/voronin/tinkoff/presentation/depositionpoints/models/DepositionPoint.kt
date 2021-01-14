package com.voronin.tinkoff.presentation.depositionpoints.models

import android.os.Parcelable
import com.voronin.tinkoff.utils.list.Similarable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class DepositionPoint(

    val id: String,

    val partnerName: String,

    val location: LocationGeo,

    val workHours: String,

    val phones: String,

    val addressInfo: String,

    val fullAddress: String,

    val images: ImageInfo,

) : Parcelable, Similarable<DepositionPoint> {

    override fun areItemsTheSame(other: DepositionPoint): Boolean {
        return this.id == other.id
    }

    override fun areContentsTheSame(other: DepositionPoint): Boolean {
        return this == other
    }
}
