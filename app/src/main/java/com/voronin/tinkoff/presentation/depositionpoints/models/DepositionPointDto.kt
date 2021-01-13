package com.voronin.tinkoff.presentation.depositionpoints.models

import com.voronin.api.dto.LocationDto

data class DepositionPoint(

    val partnerName: String,

    val location: LocationDto,

    val workHours: String,

    val phones: String,

    val addressInfo: String,

    val fullAddress: String,

    )
