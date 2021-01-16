package com.voronin.tinkoff.presentation.depositionpoints.map

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.MapView
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.voronin.tinkoff.R
import com.voronin.tinkoff.data.base.OperationState
import com.voronin.tinkoff.presentation.base.BaseLocationFragment
import com.voronin.tinkoff.presentation.depositionpoints.detail.DepositionPointFragment
import com.voronin.tinkoff.presentation.depositionpoints.models.DepositionPoint
import com.voronin.tinkoff.presentation.depositionpoints.models.LocationGeo
import com.voronin.tinkoff.presentation.views.map.MapViewFragmentLifecycleCallback
import com.voronin.tinkoff.presentation.views.map.MapViewProvider
import com.voronin.tinkoff.utils.ext.calculateVisibleRadius
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

    override fun onSuccessLocationListener() {
        lastLocation?.let {
            moveCameraToLocation(it)
        }
    }

    override fun onLocationEnabled() {
        Log.d("voronin", "onLocationEnabled")
    }

    override fun onLocationDenied() {
        Log.d("voronin", "onLocationDenied")
    }

    override fun getMapView(): MapView? = depositionPointsMapMapView

    override fun callOperations() = Unit

    override fun onSetupLayout(savedInstanceState: Bundle?) {
        depositionPointsMapStateViewFlipper.changeState(OperationState.success())
        depositionPointsMapStateViewFlipper.setRetryMethod {
            requestLocationPermission()
            googleMap?.let { refreshMap(it) }
        }

        depositionPointsMapMapView.getMapAsync(this)

        requireActivity().supportFragmentManager.registerFragmentLifecycleCallbacks(
            MapViewFragmentLifecycleCallback, true
        )
        requestLocationPermission()
    }

    override fun onBindViewModel() = with(viewModel) {
        depositionsListViewModel.markersLiveData.observe { depositionPoints ->
            depositionPoints.forEach {
                addMarker(it)
            }
        }
        depositionsListViewModel.markersProgressLiveData.observe {
            depositionPointsMapStateViewFlipper.changeState(it)
        }
        openDepositionPointDetail.observe { point ->
            DepositionPointFragment.newInstance(point).show(childFragmentManager, "")
        }
    }

    private fun moveCameraToLocation(locationGeo: LocationGeo) {
        val location = LatLng(
            locationGeo.latitude,
            locationGeo.longitude
        )

        val cameraUpdate = CameraUpdateFactory.newLatLng(location)
        val zoom = CameraUpdateFactory.zoomTo(13f)

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
        }

        googleMap.setMinZoomPreference(13.0f)
        googleMap.setMaxZoomPreference(16.0f)

        var initLocationView = true
        googleMap.setOnCameraChangeListener {
            if (lastLocation != null && initLocationView) {
                refreshMap(googleMap)
                initLocationView = false
            }
        }
    }

    private fun refreshMap(googleMap: GoogleMap) {
        viewModel.depositionsListViewModel.getPoints(
            lastLocation,
            radius = googleMap.calculateVisibleRadius()
        )
    }
}
