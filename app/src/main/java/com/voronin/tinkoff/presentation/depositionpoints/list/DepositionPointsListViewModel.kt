package com.voronin.tinkoff.presentation.depositionpoints.list

import androidx.lifecycle.MutableLiveData
import com.voronin.tinkoff.presentation.base.BaseViewModel
import com.voronin.tinkoff.presentation.depositionpoints.models.DepositionPoint
import com.voronin.tinkoff.presentation.depositionpoints.viewer.DepositionsListViewModel
import javax.inject.Inject

class DepositionPointsListViewModel @Inject constructor(
    val depositionsListViewModel: DepositionsListViewModel,
) : BaseViewModel() {

    val openDepositionPointDetail = MutableLiveData<DepositionPoint>()

    fun onItemSelected(depositionPoint: DepositionPoint) {
        openDepositionPointDetail.postValue(depositionPoint)
    }
}
