package com.voronin.tinkoff.presentation.base

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.location.LocationManager
import android.location.LocationManager.GPS_PROVIDER
import android.os.Bundle
import android.view.View
import androidx.annotation.LayoutRes
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices
import com.tbruyelle.rxpermissions2.RxPermissions
import com.voronin.tinkoff.presentation.depositionpoints.models.LocationGeo
import io.reactivex.disposables.Disposable

abstract class BaseLocationFragment(@LayoutRes layout: Int) : BaseFragment(layout) {

    companion object {
        private const val REQUEST_SETTINGS = 1050

        private const val UPDATE_INTERVAL_IN_MILLISECONDS: Long = 10000
        private const val FASTEST_UPDATE_INTERVAL_IN_MILLISECONDS: Long = UPDATE_INTERVAL_IN_MILLISECONDS / 2
    }

    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private lateinit var locationManager: LocationManager
    private lateinit var locationRequest: LocationRequest
    private lateinit var locationCallback: LocationCallback

    protected var lastLocation: LocationGeo? = null

    private var disposable: Disposable? = null
    private var defaultLocationGeo = false

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        locationManager = activity?.getSystemService(Context.LOCATION_SERVICE) as LocationManager
        super.onViewCreated(view, savedInstanceState)
        createLocationRequest()
        createLocationCallback()
    }

    override fun onPause() {
        if (::fusedLocationClient.isInitialized) {
            fusedLocationClient.removeLocationUpdates(locationCallback)
        }
        super.onPause()
    }

    fun requestLocationPermission(fromResult: Boolean = false) {
//        permissionDialog?.dismiss()
        disposable?.dispose()
        disposable = RxPermissions(this).requestEachCombined(
            Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.ACCESS_FINE_LOCATION
        )
            .subscribe {
                if (it.granted) {
                    onLocationPermissionGranted()
                } else if (it.shouldShowRequestPermissionRationale) {
                    onLocationDenied()
                }
            }
    }

    override fun onDestroyView() {
        disposable?.dispose()
        super.onDestroyView()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_SETTINGS) {
            requestLocationPermission(true)
        }
    }

    protected abstract fun onSuccessLocationListener()

    @SuppressLint("MissingPermission")
    private fun onLocationPermissionGranted() = with(locationManager) {
        val hasNotGpsProvider = !allProviders.contains(GPS_PROVIDER)
        val isProviderEnabled = isProviderEnabled(GPS_PROVIDER)
        val hasNotNetworkProvider = !allProviders.contains(LocationManager.NETWORK_PROVIDER)
        val isNetworkProviderEnabled = isProviderEnabled(LocationManager.NETWORK_PROVIDER)

        if (hasNotGpsProvider || isProviderEnabled || hasNotNetworkProvider || isNetworkProviderEnabled) {
            fusedLocationClient = LocationServices.getFusedLocationProviderClient(requireContext())
            fusedLocationClient.lastLocation.addOnSuccessListener {
                if (it != null) {
                    lastLocation = LocationGeo(
                        it.latitude,
                        it.longitude
                    )
                    onSuccessLocationListener()
                    defaultLocationGeo = true
                } else {
                    fusedLocationClient.requestLocationUpdates(locationRequest, locationCallback, null)
                }
            }
            onLocationEnabled()
        }
    }

    private fun createLocationRequest() {
        locationRequest = LocationRequest()

        locationRequest.interval = UPDATE_INTERVAL_IN_MILLISECONDS
        locationRequest.fastestInterval = FASTEST_UPDATE_INTERVAL_IN_MILLISECONDS
        locationRequest.priority = LocationRequest.PRIORITY_HIGH_ACCURACY
    }

    private fun createLocationCallback() {
        locationCallback = object : LocationCallback() {
            override fun onLocationResult(locationResult: LocationResult?) {
                locationResult?.lastLocation?.let { it ->
                    lastLocation = LocationGeo(it.latitude, it.longitude)
                    fusedLocationClient.removeLocationUpdates(locationCallback)
                    onSuccessLocationListener()
                }
            }
        }
    }

    protected abstract fun onLocationEnabled()

    protected abstract fun onLocationDenied()
}
