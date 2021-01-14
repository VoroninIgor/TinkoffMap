package com.voronin.tinkoff.presentation.depositionpoints.list

import androidx.lifecycle.MutableLiveData
import com.voronin.tinkoff.data.base.OperationState
import com.voronin.tinkoff.data.base.SingleInteractor2
import com.voronin.tinkoff.data.repository.DepositionPointsRepo
import com.voronin.tinkoff.presentation.base.BaseViewModel
import com.voronin.tinkoff.presentation.depositionpoints.models.DepositionPoint
import com.voronin.tinkoff.presentation.depositionpoints.models.LocationGeo
import javax.inject.Inject

class DepositionPointsListViewModel @Inject constructor(
    private val depositionPointsRepo: DepositionPointsRepo,
) : BaseViewModel() {

    val markersLiveData = MutableLiveData<List<DepositionPoint>>()
    val markersProgress = MutableLiveData<OperationState>()

    val openDepositionPointDetail = MutableLiveData<DepositionPoint>()

    fun getPoints(lastLocation: LocationGeo? = null) {
        if (lastLocation == null) return
        execute(
            SingleInteractor2(
                depositionPointsRepo.getDepositionPoints(
                    latitude = lastLocation.latitude,
                    longitude = lastLocation.longitude,
                ),
                markersLiveData,
                markersProgress
            )
        )
    }

    fun onItemSelected(depositionPoint: DepositionPoint) {
        openDepositionPointDetail.postValue(depositionPoint)
    }
}
