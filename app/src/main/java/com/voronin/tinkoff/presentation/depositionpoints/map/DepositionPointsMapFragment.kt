package com.voronin.tinkoff.presentation.depositionpoints.map

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.MapView
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.voronin.tinkoff.R
import com.voronin.tinkoff.data.base.OperationState
import com.voronin.tinkoff.presentation.base.BaseLocationFragment
import com.voronin.tinkoff.presentation.depositionpoints.detail.DepositionPointFragment
import com.voronin.tinkoff.presentation.depositionpoints.models.DepositionPoint
import com.voronin.tinkoff.presentation.views.map.MapViewFragmentLifecycleCallback
import com.voronin.tinkoff.presentation.views.map.MapViewProvider
import com.voronin.tinkoff.utils.ext.calculateVisibleRadius
import kotlinx.android.synthetic.main.fragment_depositions_points_map.depositionPointsMapStateViewFlipper
import kotlinx.android.synthetic.main.fragment_depositions_points_map.fabDepositionPointsMapMyLocation
import kotlinx.android.synthetic.main.fragment_depositions_points_map.fabDepositionPointsMapZoomIn
import kotlinx.android.synthetic.main.fragment_depositions_points_map.fabDepositionPointsMapZoomOut
import kotlinx.android.synthetic.main.fragment_depositions_points_map.mapViewDepositionPointsMap
import kotlin.math.min

class DepositionPointsMapFragment private constructor() :
    BaseLocationFragment(R.layout.fragment_depositions_points_map),
    OnMapReadyCallback,
    MapViewProvider {

    companion object {
        fun newInstance(): DepositionPointsMapFragment {
            return DepositionPointsMapFragment()
        }

        private const val MIN_ZOOM = 12f
        private const val MAX_ZOOM = 16f

        private const val MY_LOCATION_ZOOM = 14f
    }

    private val viewModel: DepositionPointsMapViewModel by appViewModels()

    private var googleMap: GoogleMap? = null

    override fun onSuccessLocationListener() {
        addMyLocationMarker()
        moveCameraToLocation()
        Log.d("voronin", "onSuccessLocationListener")
    }

    override fun onLocationEnabled() {
        Log.d("voronin", "onLocationEnabled")
    }

    override fun onLocationDenied() {
        depositionPointsMapStateViewFlipper.changeState(OperationState.error())
        Log.d("voronin", "onLocationDenied")
    }

    override fun getMapView(): MapView? = mapViewDepositionPointsMap

    override fun callOperations() = Unit

    override fun onSetupLayout(savedInstanceState: Bundle?) {
        depositionPointsMapStateViewFlipper.changeState(OperationState.success())
        depositionPointsMapStateViewFlipper.setRetryMethod {
            requestLocationPermission()
            depositionPointsMapStateViewFlipper.changeState(OperationState.success())
            googleMap?.let { refreshMap(it) }
        }

        fabDepositionPointsMapZoomOut.setOnClickListener {
            googleMap?.animateCamera(CameraUpdateFactory.zoomTo(min(getZoomValue() - 1, MIN_ZOOM)))
        }
        fabDepositionPointsMapZoomIn.setOnClickListener {
            googleMap?.animateCamera(CameraUpdateFactory.zoomTo(min(getZoomValue() + 1, MAX_ZOOM)))
        }
        fabDepositionPointsMapMyLocation.setOnClickListener {
            moveCameraToLocation()
        }

        mapViewDepositionPointsMap.getMapAsync(this)

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

    private fun moveCameraToLocation() {
        lastLocation?.let {
            val location = LatLng(it.latitude, it.longitude)
            val cameraUpdate = CameraUpdateFactory.newLatLng(location)
            val zoom = CameraUpdateFactory.zoomTo(MY_LOCATION_ZOOM)

            googleMap?.moveCamera(cameraUpdate)
            googleMap?.animateCamera(zoom)
        }
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

    private fun addMyLocationMarker() {
        lastLocation?.let {
            googleMap?.addMarker(
                MarkerOptions()
                    .position(LatLng(it.latitude, it.longitude))
                    .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN))
            )
        }
    }

    @SuppressLint("PotentialBehaviorOverride")
    private fun initGoogleMap(googleMap: GoogleMap) = with(googleMap) {
        setOnMarkerClickListener { marker ->
            if (marker.tag is DepositionPoint) {
                viewModel.onMarkerClick(marker.tag as DepositionPoint)
                return@setOnMarkerClickListener true
            }
            false
        }
        mapType = GoogleMap.MAP_TYPE_NORMAL
        uiSettings.apply {
            isZoomControlsEnabled = false
            isZoomGesturesEnabled = true
        }

        setMinZoomPreference(MIN_ZOOM)
        setMaxZoomPreference(MAX_ZOOM)

        var initLocationView = true
        setOnCameraChangeListener {
            if (lastLocation != null) {
                if (initLocationView) {
                    refreshMap(this)
                    initLocationView = false
                }
            } else {
                lastLocation = DEFAULT_LOCATION_GEO_MSK
                depositionPointsMapStateViewFlipper.changeState(OperationState.error())
            }
        }
    }

    private fun refreshMap(googleMap: GoogleMap) {
        viewModel.depositionsListViewModel.getPoints(
            lastLocation,
            radius = googleMap.calculateVisibleRadius()
        )
    }

    private fun getZoomValue(): Float = googleMap?.cameraPosition?.zoom ?: 0f
}
