package com.voronin.tinkoff.utils.ext

import android.location.Location
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.VisibleRegion

fun GoogleMap.calculateVisibleRadius(): Int {
    val vr: VisibleRegion = projection.visibleRegion

    val center = Location("center")
    center.latitude = vr.latLngBounds.center.latitude
    center.longitude = vr.latLngBounds.center.longitude

    val top = Location("top")
    top.latitude = vr.latLngBounds.northeast.latitude
    top.longitude = vr.latLngBounds.northeast.longitude

    val dis: Float = center.distanceTo(top)

    return dis.toInt()
}
