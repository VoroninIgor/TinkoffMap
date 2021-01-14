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

    companion object {
        private const val DEFAULT_RADIUS = 1000
    }

    fun getDepositionPoints(
        latitude: Double,
        longitude: Double,
    ): Single<List<DepositionPoint>> {
        return apiClient.getDepositionPoints(latitude, longitude, DEFAULT_RADIUS)
            .map { it.map { apiItem -> depositionPointMapper.fromApiToModel(apiItem) } }
    }
}
