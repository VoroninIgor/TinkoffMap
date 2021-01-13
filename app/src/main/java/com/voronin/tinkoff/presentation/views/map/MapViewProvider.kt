package com.voronin.tinkoff.presentation.views.map

import com.google.android.gms.maps.MapView

interface MapViewProvider {

    fun getMapView(): MapView?
}
