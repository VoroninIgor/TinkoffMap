package com.voronin.tinkoff.presentation.depositionpoints.map

import android.os.Bundle
import android.util.Log
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.MapView
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.voronin.tinkoff.R
import com.voronin.tinkoff.presentation.base.BaseFragment
import com.voronin.tinkoff.presentation.depositionpoints.models.DepositionPoint
import com.voronin.tinkoff.presentation.views.map.MapViewFragmentLifecycleCallback
import com.voronin.tinkoff.presentation.views.map.MapViewProvider
import kotlinx.android.synthetic.main.fragment_depositions_points_map.depositionPointsMapMapView


class DepositionPointsMapFragment : BaseFragment(R.layout.fragment_depositions_points_map), OnMapReadyCallback, MapViewProvider {

    private val viewModel: DepositionPointsMapViewModel by appViewModels()

    private var googleMap: GoogleMap? = null

    override fun getMapView(): MapView? = depositionPointsMapMapView

    override fun callOperations() {
        viewModel.getPoints()
    }

    override fun onSetupLayout(savedInstanceState: Bundle?) {
        depositionPointsMapMapView.getMapAsync(this)

        requireActivity().supportFragmentManager.registerFragmentLifecycleCallbacks(
            MapViewFragmentLifecycleCallback, true
        )
    }

    override fun onBindViewModel() = with(viewModel) {
        markersLiveData.observe { depositionPoints ->
            depositionPoints.forEach {
                addMarker(it)
            }
        }
        markersProgress.observe {
            Log.d("", "")
            // TODO
        }
    }

    override fun onMapReady(googleMap: GoogleMap) {
        this.googleMap = googleMap
        initGoogleMap(googleMap)
    }

    private fun addMarker(depositionPoint: DepositionPoint) {
        val location = depositionPoint.location
        googleMap?.addMarker(MarkerOptions()
            .position(
                LatLng(
                    location.latitude.toDouble(),
                    location.longitude.toDouble()
                )
            )
            .title(depositionPoint.partnerName))
    }

    private fun initGoogleMap(googleMap: GoogleMap) {
        googleMap.mapType = GoogleMap.MAP_TYPE_NORMAL
        googleMap.uiSettings.apply {
            isZoomControlsEnabled = true
            isZoomGesturesEnabled = true
        }
    }
}