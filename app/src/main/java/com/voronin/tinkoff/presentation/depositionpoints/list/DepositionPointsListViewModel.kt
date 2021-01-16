package com.voronin.tinkoff.presentation.depositionpoints.list

import androidx.lifecycle.MutableLiveData
import com.voronin.tinkoff.data.base.SingleInteractor2
import com.voronin.tinkoff.data.repository.DepositionPointsRepo
import com.voronin.tinkoff.presentation.base.BaseViewModel
import com.voronin.tinkoff.presentation.depositionpoints.models.DepositionPoint
import com.voronin.tinkoff.presentation.depositionpoints.viewer.DepositionsListViewModel
import javax.inject.Inject

class DepositionPointsListViewModel @Inject constructor(
    val depositionsListViewModel: DepositionsListViewModel,
    private val depositionPointsRepo: DepositionPointsRepo,
) : BaseViewModel() {

    val openDetailLiveData = MutableLiveData<DepositionPoint>()

    val viewedListLiveData = MutableLiveData<List<DepositionPoint>>()

    fun onItemSelected(depositionPoint: DepositionPoint) {
        openDetailLiveData.postValue(depositionPoint)
    }

    fun refreshViewedPoint() {
        execute(
            SingleInteractor2(
                depositionPointsRepo.getAllPointsFromDatabase(),
                viewedListLiveData,
                null
            )
        )
    }
}
