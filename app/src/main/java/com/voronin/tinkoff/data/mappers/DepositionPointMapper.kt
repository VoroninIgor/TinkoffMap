package com.voronin.tinkoff.data.mappers

import com.voronin.api.dto.DepositionPointDto
import com.voronin.api.dto.LocationDto
import com.voronin.tinkoff.presentation.depositionpoints.models.DepositionPoint
import com.voronin.tinkoff.presentation.depositionpoints.models.LocationGeo
import javax.inject.Inject

class DepositionPointMapper @Inject constructor() {

    fun fromApiToModel(api: DepositionPointDto): DepositionPoint {
        return DepositionPoint(
            partnerName = api.partnerName,
            location = fromApiToModel(api.location),
            workHours = api.workHours ?: "",
            phones = api.phones ?: "",
            addressInfo = api.addressInfo ?: "",
            fullAddress = api.fullAddress ?: "",
        )
    }

    private fun fromApiToModel(api: LocationDto): LocationGeo {
        return LocationGeo(
            latitude = api.latitude,
            longitude = api.longitude
        )
    }
}