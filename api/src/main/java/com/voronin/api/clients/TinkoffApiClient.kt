package com.voronin.api.clients

import com.voronin.api.dto.DepositionPartnerDto
import com.voronin.api.dto.DepositionPointDto
import io.reactivex.Single

interface TinkoffApiClient {

    fun getDepositionPoints(
        latitude: Double,
        longitude: Double,
        radius: Int,
    ): Single<List<DepositionPointDto>>

    fun getDepositionPartners(
        accountType: String,
    ): Single<List<DepositionPartnerDto>>
}
