package com.voronin.tinkoff.data.mappers

import com.voronin.api.dto.DepositionPointDto
import com.voronin.tinkoff.presentation.depositionpoints.models.DepositionPoint
import javax.inject.Inject

class DepositionPointMapper @Inject constructor() {

    fun fromApiToModel(api: DepositionPointDto): DepositionPoint {
        return DepositionPoint(
            partnerName = api.partnerName,
            location = api.location,
            workHours = api.workHours ?: "",
            phones = api.phones ?: "",
            addressInfo = api.addressInfo ?: "",
            fullAddress = api.fullAddress ?: "",
        )
    }
}