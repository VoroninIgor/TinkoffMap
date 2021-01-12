package com.voronin.api.clients

import com.voronin.api.dto.DepositionPointDto
import io.reactivex.Single

interface TinkoffApiClient {

    fun getDepositionPoints(
        latitude: Float,
        longitude: Float,
        radius: Int,
    ): Single<List<DepositionPointDto>>
}