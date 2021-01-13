package com.voronin.api.dto

import com.google.gson.annotations.SerializedName

data class LocationDto(

    @SerializedName("latitude")
    val latitude: Double,

    @SerializedName("longitude")
    val longitude: Double,

    )
