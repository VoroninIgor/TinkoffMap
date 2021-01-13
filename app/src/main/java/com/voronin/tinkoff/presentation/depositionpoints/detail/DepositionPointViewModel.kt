package com.voronin.tinkoff.presentation.depositionpoints.detail

import androidx.lifecycle.MutableLiveData
import com.voronin.tinkoff.presentation.base.BaseViewModel
import com.voronin.tinkoff.presentation.depositionpoints.models.DepositionPoint
import javax.inject.Inject

class DepositionPointViewModel @Inject constructor() : BaseViewModel() {

    val depositionPointLiveData = MutableLiveData<DepositionPoint>()

    fun initDepositionPoint(depositionPoint: DepositionPoint) {
        depositionPointLiveData.postValue(depositionPoint)
    }
}
