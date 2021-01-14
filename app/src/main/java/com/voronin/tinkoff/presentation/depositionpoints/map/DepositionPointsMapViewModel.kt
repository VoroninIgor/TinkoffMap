package com.voronin.tinkoff.presentation.depositionpoints.map

import androidx.lifecycle.MutableLiveData
import com.voronin.tinkoff.data.base.OperationState
import com.voronin.tinkoff.data.base.SingleInteractor2
import com.voronin.tinkoff.data.repository.DepositionPointsRepo
import com.voronin.tinkoff.presentation.base.BaseViewModel
import com.voronin.tinkoff.presentation.depositionpoints.models.DepositionPoint
import com.voronin.tinkoff.presentation.depositionpoints.models.LocationGeo
import javax.inject.Inject

class DepositionPointsMapViewModel @Inject constructor(
    private val depositionPointsRepo: DepositionPointsRepo,
) : BaseViewModel() {

    val markersLiveData = MutableLiveData<List<DepositionPoint>>()
    val markersProgress = MutableLiveData<OperationState>()

    val openDepositionPointDetail = MutableLiveData<DepositionPoint>()

    fun getPoints(lastLocation: LocationGeo? = null) {
        execute(
            SingleInteractor2(
                depositionPointsRepo.getDepositionPoints(
                    latitude = lastLocation?.latitude ?: 0.0,
                    longitude = lastLocation?.longitude ?: 0.0,
                    radius = 1000 // FIXME
                ),
                markersLiveData,
                markersProgress
            )
        )
    }

    fun onMarkerClick(depositionPoint: DepositionPoint) {
        openDepositionPointDetail.postValue(depositionPoint)
    }
}
