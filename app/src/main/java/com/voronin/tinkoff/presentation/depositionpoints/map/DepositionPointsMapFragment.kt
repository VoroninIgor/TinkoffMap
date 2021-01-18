package com.voronin.tinkoff.presentation.depositionpoints.map

import android.annotation.SuppressLint
import android.os.Bundle
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.MapView
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.voronin.tinkoff.R
import com.voronin.tinkoff.presentation.base.BaseLocationFragment
import com.voronin.tinkoff.presentation.depositionpoints.detail.DepositionPointFragment
import com.voronin.tinkoff.presentation.depositionpoints.models.DepositionPoint
import com.voronin.tinkoff.presentation.depositionpoints.models.LocationGeo
import com.voronin.tinkoff.presentation.views.map.MapViewFragmentLifecycleCallback
import com.voronin.tinkoff.presentation.views.map.MapViewProvider
import com.voronin.tinkoff.utils.ext.calculateVisibleRadius
import kotlinx.android.synthetic.main.fragment_depositions_points_map.depositionPointsMapStateViewFlipper
import kotlinx.android.synthetic.main.fragment_depositions_points_map.fabDepositionPointsMapMyLocation
import kotlinx.android.synthetic.main.fragment_depositions_points_map.fabDepositionPointsMapZoomIn
import kotlinx.android.synthetic.main.fragment_depositions_points_map.fabDepositionPointsMapZoomOut
import kotlinx.android.synthetic.main.fragment_depositions_points_map.mapViewDepositionPointsMap
import kotlin.math.max
import kotlin.math.min

class DepositionPointsMapFragment private constructor() :
    BaseLocationFragment(R.layout.fragment_depositions_points_map),
    OnMapReadyCallback,
    MapViewProvider {

    companion object {
        fun newInstance(): DepositionPointsMapFragment {
            return DepositionPointsMapFragment()
        }

        private const val MIN_ZOOM = 10f
        private const val MAX_ZOOM = 16f

        private const val MY_LOCATION_ZOOM = 14f
        private const val MY_LOCATION_RADIUS = 1500 // метры
    }

    private val viewModel: DepositionPointsMapViewModel by appViewModels()

    private var googleMap: GoogleMap? = null

    override fun onSuccessLocationListener() {
        addMyLocationMarker()
        moveCameraToLocation(lastLocation)
    }

    override fun onLocationEnabled() {
        depositionPointsMapStateViewFlipper.setStateData()
    }

    override fun onLocationDenied() {
        showDefaultView()
    }

    private fun showDefaultView() {
        lastLocation = DEFAULT_LOCATION_GEO_MSK
        updatePoints(MY_LOCATION_RADIUS)
        moveCameraToLocation(DEFAULT_LOCATION_GEO_MSK)
    }

    override fun getMapView(): MapView? = mapViewDepositionPointsMap

    override fun callOperations() = Unit

    override fun onSetupLayout(savedInstanceState: Bundle?) {
        depositionPointsMapStateViewFlipper.setStateData()
        depositionPointsMapStateViewFlipper.setRetryMethod {
            requestLocationPermission()
        }

        fabDepositionPointsMapZoomOut.setOnClickListener {
            googleMap?.animateCamera(CameraUpdateFactory.zoomTo(max(getZoomValue() - 1, MIN_ZOOM)))
        }
        fabDepositionPointsMapZoomIn.setOnClickListener {
            googleMap?.animateCamera(CameraUpdateFactory.zoomTo(min(getZoomValue() + 1, MAX_ZOOM)))
        }
        fabDepositionPointsMapMyLocation.setOnClickListener {
            moveCameraToLocation(lastLocation)
            if (viewModel.depositionsListViewModel.markersLiveData.value?.isEmpty() == true) {
                showDefaultView()
            }
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

    private fun moveCameraToLocation(viewedLocation: LocationGeo?) {
        viewedLocation?.let {
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
                marker.setIcon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_ORANGE))
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

        moveCameraToLocation(DEFAULT_LOCATION_GEO_MSK)

        var initLocationView = true
        setOnCameraMoveStartedListener {
            if (lastLocation != null) {
                if (initLocationView) {
                    refreshMap(this)
                    initLocationView = false
                }
            }
        }
    }

    private fun refreshMap(googleMap: GoogleMap) {
        updatePoints(googleMap.calculateVisibleRadius())
    }

    private fun updatePoints(radius: Int) {
        viewModel.depositionsListViewModel.getPoints(
            lastLocation,
            radius = radius
        )
    }

    private fun getZoomValue(): Float = googleMap?.cameraPosition?.zoom ?: 0f
}
