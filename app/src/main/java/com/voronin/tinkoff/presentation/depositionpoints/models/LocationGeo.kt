package com.voronin.tinkoff.presentation.depositionpoints.models

import android.os.Parcelable
import com.voronin.tinkoff.utils.list.Similarable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class LocationGeo(

    val latitude: Double,

    val longitude: Double,

    ) : Parcelable, Similarable<LocationGeo> {

    override fun areItemsTheSame(other: LocationGeo): Boolean {
        return this.latitude == other.latitude && this.longitude == other.longitude
    }

    override fun areContentsTheSame(other: LocationGeo): Boolean {
        return this == other
    }
}
