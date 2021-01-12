package com.voronin.api.clients

import com.voronin.api.TinkoffApiService
import com.voronin.api.dto.DepositionPointDto
import io.reactivex.Single
import javax.inject.Inject

internal class TinkoffApiClientImpl @Inject constructor(
     val api: TinkoffApiService,
) : TinkoffApiClient {

    override fun getDepositionPoints(latitude: Float, longitude: Float, radius: Int): Single<List<DepositionPointDto>> {
        return api.getAllDepositionPoints(
             latitude = latitude,
             longitude = longitude,
             radius = radius
        ).map { it.payload }
    }
}
