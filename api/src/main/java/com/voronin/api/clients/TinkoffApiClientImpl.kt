package com.voronin.api.clients

import com.voronin.api.TinkoffApiService
import com.voronin.api.dto.DepositionPartnerDto
import com.voronin.api.dto.DepositionPointDto
import io.reactivex.Single
import javax.inject.Inject

internal class TinkoffApiClientImpl @Inject constructor(
    private val api: TinkoffApiService,
) : TinkoffApiClient {

    override fun getDepositionPoints(latitude: Double, longitude: Double, radius: Int): Single<List<DepositionPointDto>> {
        return api.getAllDepositionPoints(
            latitude = latitude,
            longitude = longitude,
            radius = radius
        ).map { it.payload }
    }

    override fun getDepositionPartners(accountType: String): Single<List<DepositionPartnerDto>> {
        return api.getDepositionPartners(accountType)
            .map { it.payload }
    }
}
