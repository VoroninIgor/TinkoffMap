package com.voronin.tinkoff.presentation.depositionpoints.viewer

import androidx.lifecycle.MutableLiveData
import com.voronin.tinkoff.data.base.OperationState
import com.voronin.tinkoff.data.base.SingleInteractorWithState
import com.voronin.tinkoff.data.repository.DepositionPointsRepo
import com.voronin.tinkoff.presentation.base.BaseViewModel
import com.voronin.tinkoff.presentation.depositionpoints.models.DepositionPoint
import com.voronin.tinkoff.presentation.depositionpoints.models.LocationGeo
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DepositionsListViewModel @Inject constructor(
    private val depositionPointsRepo: DepositionPointsRepo,
) : BaseViewModel() {

    val markersLiveData = MutableLiveData<List<DepositionPoint>>()
    val markersProgressLiveData = MutableLiveData<OperationState>()

    fun getPoints(lastLocation: LocationGeo? = null, radius: Int = 1000) {
        if (lastLocation == null) return

        execute(
            SingleInteractorWithState(
                depositionPointsRepo.getDepositionPoints(
                    latitude = lastLocation.latitude,
                    longitude = lastLocation.longitude,
                    radius = radius
                ),
                markersLiveData,
                markersProgressLiveData
            )
        )
    }
}
