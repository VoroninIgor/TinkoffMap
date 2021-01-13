package com.voronin.tinkoff.data.repository

import com.voronin.api.clients.TinkoffApiClient
import com.voronin.tinkoff.data.mappers.DepositionPointMapper
import com.voronin.tinkoff.presentation.depositionpoints.models.DepositionPoint
import io.reactivex.Single
import javax.inject.Inject

class DepositionPointsRepo @Inject constructor(
    private val apiClient: TinkoffApiClient,
    private val depositionPointMapper: DepositionPointMapper,
) {

    fun getDepositionPoints(): Single<List<DepositionPoint>> {
        //FIXME 55.699950, 37.772501
        return apiClient.getDepositionPoints(55.699950f, 37.772501f, 1000)
            .map { it.map { apiItem -> depositionPointMapper.fromApiToModel(apiItem) } }
    }
}