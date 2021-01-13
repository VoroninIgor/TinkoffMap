package com.voronin.tinkoff.presentation.depositionpoints.map

import androidx.lifecycle.MutableLiveData
import com.google.android.gms.maps.model.Marker
import com.voronin.tinkoff.data.base.OperationState
import com.voronin.tinkoff.data.base.SingleInteractor2
import com.voronin.tinkoff.data.repository.DepositionPointsRepo
import com.voronin.tinkoff.presentation.base.BaseViewModel
import com.voronin.tinkoff.presentation.depositionpoints.models.DepositionPoint
import javax.inject.Inject

class DepositionPointsMapViewModel @Inject constructor(
    private val depositionPointsRepo: DepositionPointsRepo,
) : BaseViewModel() {

    val markersLiveData = MutableLiveData<List<DepositionPoint>>()
    val markersProgress = MutableLiveData<OperationState>()

    fun getPoints() {
        execute(SingleInteractor2(
            depositionPointsRepo.getDepositionPoints(),
            markersLiveData,
            markersProgress
        ))
    }

    fun onMarkerClick(marker: Marker?) {

    }
}