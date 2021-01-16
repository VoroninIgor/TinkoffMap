package com.voronin.tinkoff.data.mappers

import com.voronin.api.dto.DepositionPartnerDto
import com.voronin.api.dto.DepositionPointDto
import com.voronin.api.dto.LocationDto
import com.voronin.tinkoff.db.entities.DepositionPartnerEntity
import com.voronin.tinkoff.db.entities.DepositionPointEntity
import com.voronin.tinkoff.presentation.depositionpoints.models.DepositionPoint
import com.voronin.tinkoff.presentation.depositionpoints.models.ImageInfo
import com.voronin.tinkoff.presentation.depositionpoints.models.LocationGeo
import javax.inject.Inject

class DepositionMapper @Inject constructor() {

    fun fromApiToModel(api: DepositionPointDto): DepositionPoint {
        return DepositionPoint(
            id = api.externalId,
            partnerName = api.partnerName,
            location = fromApiToModel(api.location),
            workHours = api.workHours ?: "",
            phones = api.phones ?: "",
            addressInfo = api.addressInfo ?: "",
            fullAddress = api.fullAddress ?: "",
            images = ImageInfo(),
        )
    }

    fun fromApiToEntity(api: DepositionPartnerDto): DepositionPartnerEntity {
        return DepositionPartnerEntity(
            id = api.id,
            name = api.name,
            picture = api.picture,
        )
    }

    fun fromModelToEntity(model: DepositionPoint): DepositionPointEntity {
        return DepositionPointEntity(
            id = model.id,
            partnerName = model.partnerName,
            location = model.location,
            workHours = model.workHours,
            phones = model.phones,
            addressInfo = model.addressInfo,
            fullAddress = model.fullAddress,
            images = model.images,
        )
    }

    fun fromEntityToModel(entity: DepositionPointEntity): DepositionPoint {
        return DepositionPoint(
            id = entity.id,
            partnerName = entity.partnerName,
            location = entity.location,
            workHours = entity.workHours,
            phones = entity.phones,
            addressInfo = entity.addressInfo,
            fullAddress = entity.fullAddress,
            images = entity.images,
        )
    }

    fun fromEntityToModel(entity: DepositionPartnerEntity): DepositionPartnerDto {
        return DepositionPartnerDto(
            id = entity.id,
            name = entity.name,
            picture = entity.picture,
        )
    }

    private fun fromApiToModel(api: LocationDto): LocationGeo {
        return LocationGeo(
            latitude = api.latitude,
            longitude = api.longitude
        )
    }

    private fun fromApiToModel(api: ImageInfo): ImageInfo {
        return ImageInfo(
            smallImageUrl = api.smallImageUrl,
            mediumImageUrl = api.mediumImageUrl,
            highImageUrl = api.highImageUrl,
            largeImageUrl = api.largeImageUrl,
        )
    }
}
