package com.voronin.tinkoff.presentation.base

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.location.LocationManager
import android.location.LocationManager.GPS_PROVIDER
import android.net.Uri
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import android.view.View
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AlertDialog
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices
import com.tbruyelle.rxpermissions2.RxPermissions
import com.voronin.tinkoff.R
import com.voronin.tinkoff.presentation.depositionpoints.models.LocationGeo
import com.voronin.tinkoff.utils.GpsUtils
import com.voronin.tinkoff.utils.GpsUtils.Companion.GPS_REQUEST
import io.reactivex.disposables.Disposable

abstract class BaseLocationFragment(@LayoutRes layout: Int) : BaseFragment(layout) {

    companion object {
        private const val UPDATE_INTERVAL_IN_MILLISECONDS = 10000L
        private const val FASTEST_UPDATE_INTERVAL_IN_MILLISECONDS = UPDATE_INTERVAL_IN_MILLISECONDS / 2L

        val DEFAULT_LOCATION_GEO_MSK = LocationGeo(55.75231250290663, 37.61736397833792)
    }

    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private lateinit var locationManager: LocationManager
    private lateinit var locationRequest: LocationRequest
    private lateinit var locationCallback: LocationCallback

    protected var lastLocation: LocationGeo? = null

    private var disposable: Disposable? = null
    private var defaultLocationGeo = false
    private var enableGeo = false

    private var permissionDialog: AlertDialog? = null

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

    fun requestLocationPermission() {
        Log.d("voronin", "requestLocationPermission")
        disposable?.dispose()
        disposable = RxPermissions(this).requestEachCombined(
            Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.ACCESS_FINE_LOCATION
        )
            .subscribe {
                if (it.granted) {
                    enableGeo = true
                    onLocationPermissionGranted()
                    if (!locationManager.isProviderEnabled(GPS_PROVIDER)) {
                        showEnableGpsAlert()
                    }
                } else if (it.shouldShowRequestPermissionRationale) {
                    onLocationDenied()
                } else {
                    if (!enableGeo) {
                        showEnableGpsForAppAlert()
                    }
                }
            }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == GPS_REQUEST) {
                onLocationPermissionGranted()
            }
        }
    }

    private fun showEnableGpsAlert() {
        permissionDialog?.dismiss()
        permissionDialog = AlertDialog.Builder(requireContext(), R.style.AlertStyle)
            .setTitle(" ")
            .setIcon(R.drawable.ic_gps)
            .setMessage(R.string.need_location_enable)
            .setPositiveButton(getString(R.string.settings)) { dialog, _ ->
                GpsUtils(requireContext()).turnGPSOn {
                    onLocationEnabled()
                }
                dialog.dismiss()
            }
            .setNegativeButton(getString(R.string.cancel)) { dialog, _ ->
                dialog.dismiss()
                onLocationDenied()
            }
            .create()
        permissionDialog?.show()
    }

    private fun showEnableGpsForAppAlert() {
        permissionDialog?.dismiss()
        permissionDialog = AlertDialog.Builder(requireContext(), R.style.AlertStyle)
            .setTitle(" ")
            .setIcon(R.drawable.ic_gps)
            .setMessage(R.string.need_location_permission)
            .setPositiveButton(getString(R.string.settings)) { dialog, _ ->
                startActivityForResult(
                    Intent(
                        Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
                        Uri.parse("package:" + context?.packageName)
                    ),
                    1050
                )
                dialog.dismiss()
            }
            .setNegativeButton(getString(R.string.cancel)) { dialog, _ ->
                dialog.dismiss()
                onLocationDenied()
            }
            .create()
        permissionDialog?.show()
    }

    override fun onDestroyView() {
        disposable?.dispose()
        super.onDestroyView()
    }

    protected abstract fun onSuccessLocationListener()

    protected abstract fun onLocationEnabled()

    protected abstract fun onLocationDenied()

    @SuppressLint("MissingPermission")
    private fun onLocationPermissionGranted() = with(locationManager) {
        val hasNotGpsProvider = !allProviders.contains(GPS_PROVIDER)
        val isProviderEnabled = isProviderEnabled(GPS_PROVIDER)
        val hasNotNetworkProvider = !allProviders.contains(LocationManager.NETWORK_PROVIDER)
        val isNetworkProviderEnabled = isProviderEnabled(LocationManager.NETWORK_PROVIDER)

        if (hasNotGpsProvider || isProviderEnabled || hasNotNetworkProvider || isNetworkProviderEnabled) {
            fusedLocationClient = LocationServices.getFusedLocationProviderClient(requireContext())
            fusedLocationClient.lastLocation.addOnSuccessListener {
                Log.d("voronin", "fusedLocationClient lastLocation")
                if (it != null) {
                    lastLocation = LocationGeo(
                        it.latitude,
                        it.longitude
                    )
                    onSuccessLocationListener()
                    defaultLocationGeo = true
                } else {
                    fusedLocationClient.requestLocationUpdates(locationRequest, locationCallback, null)
                    Log.d("voronin", "requestLocationUpdates")
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
                Log.d("voronin", "onLocationResult")
                locationResult?.lastLocation?.let { it ->
                    lastLocation = LocationGeo(it.latitude, it.longitude)
                    fusedLocationClient.removeLocationUpdates(locationCallback)
                    onSuccessLocationListener()
                }
            }
        }
    }
}
