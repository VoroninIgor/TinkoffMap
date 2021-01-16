package com.voronin.tinkoff.presentation.depositionpoints.detail

import androidx.lifecycle.MutableLiveData
import com.voronin.tinkoff.data.repository.DepositionPointsRepo
import com.voronin.tinkoff.presentation.base.BaseViewModel
import com.voronin.tinkoff.presentation.depositionpoints.models.DepositionPoint
import javax.inject.Inject

class DepositionPointViewModel @Inject constructor(
    private val depositionPointsRepo: DepositionPointsRepo,
) : BaseViewModel() {

    val depositionPointLiveData = MutableLiveData<DepositionPoint>()

    fun initDepositionPoint(depositionPoint: DepositionPoint) {
        depositionPointLiveData.postValue(depositionPoint)
    }

    fun checkPointViewed(depositionPoint: DepositionPoint) {
        execute(
            depositionPointsRepo.setPointViewed(depositionPoint),
            {},
            {}
        )
    }
}
