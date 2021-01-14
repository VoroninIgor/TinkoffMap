package com.voronin.tinkoff.presentation.depositionpoints.map

import android.annotation.SuppressLint
import android.os.Bundle
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.MapView
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.voronin.tinkoff.R
import com.voronin.tinkoff.presentation.base.BaseLocationFragment
import com.voronin.tinkoff.presentation.depositionpoints.detail.DepositionPointFragment
import com.voronin.tinkoff.presentation.depositionpoints.models.DepositionPoint
import com.voronin.tinkoff.presentation.views.map.MapViewFragmentLifecycleCallback
import com.voronin.tinkoff.presentation.views.map.MapViewProvider
import kotlinx.android.synthetic.main.fragment_depositions_points_map.depositionPointsMapMapView
import kotlinx.android.synthetic.main.fragment_depositions_points_map.depositionPointsMapStateViewFlipper

class DepositionPointsMapFragment private constructor() :
    BaseLocationFragment(R.layout.fragment_depositions_points_map),
    OnMapReadyCallback,
    MapViewProvider {

    companion object {
        fun newInstance(): DepositionPointsMapFragment {
            return DepositionPointsMapFragment()
        }
    }

    private val viewModel: DepositionPointsMapViewModel by appViewModels()

    private var googleMap: GoogleMap? = null

    override fun getMapView(): MapView? = depositionPointsMapMapView

    override fun callOperations() {
        viewModel.getPoints(lastLocation)
    }

    override fun onSuccessLocationListener() {
        lastLocation?.let {
            viewModel.getPoints(it)
        }
    }

    override fun onLocationEnabled() {
        // TODO("Not yet implemented")
    }

    override fun onLocationDenied() {
        // TODO("Not yet implemented")
    }

    override fun onSetupLayout(savedInstanceState: Bundle?) {
        depositionPointsMapMapView.getMapAsync(this)

        requireActivity().supportFragmentManager.registerFragmentLifecycleCallbacks(
            MapViewFragmentLifecycleCallback, true
        )
        requestLocationPermission()
    }

    override fun onBindViewModel() = with(viewModel) {
        markersLiveData.observe { depositionPoints ->
            moveCameraToPoints(depositionPoints)
            depositionPoints.forEach {
                addMarker(it)
            }
        }
        markersProgress.observe {
            depositionPointsMapStateViewFlipper.changeState(it)
        }
        openDepositionPointDetail.observe { point ->
            DepositionPointFragment.newInstance(point).show(childFragmentManager, "")
        }
    }

    private fun moveCameraToPoints(depositionPoints: List<DepositionPoint>) {
        if (depositionPoints.isEmpty()) return

        val locationFirst = depositionPoints.first().location
        val location = LatLng(
            locationFirst.latitude,
            locationFirst.longitude
        )
        //lastLocation
        val cameraUpdate = CameraUpdateFactory.newLatLng(location)
        val zoom = CameraUpdateFactory.zoomTo(16f)

        googleMap?.moveCamera(cameraUpdate)
        googleMap?.animateCamera(zoom)
    }

    override fun onMapReady(googleMap: GoogleMap) {
        this.googleMap = googleMap
        initGoogleMap(googleMap)
    }

    private fun addMarker(depositionPoint: DepositionPoint) {
        val location = depositionPoint.location

        val marker = googleMap?.addMarker(
            MarkerOptions()
                .position(LatLng(location.latitude, location.longitude))
                .title(depositionPoint.partnerName)
        )
        marker?.tag = depositionPoint
    }

    @SuppressLint("PotentialBehaviorOverride")
    private fun initGoogleMap(googleMap: GoogleMap) {
        googleMap.setOnMarkerClickListener { marker ->
            if (marker.tag is DepositionPoint) {
                viewModel.onMarkerClick(marker.tag as DepositionPoint)
                return@setOnMarkerClickListener true
            }

            false
        }
        googleMap.mapType = GoogleMap.MAP_TYPE_NORMAL
        googleMap.uiSettings.apply {
            isZoomControlsEnabled = true
            isZoomGesturesEnabled = true
            isMyLocationButtonEnabled = true
        }
    }
}
