package com.voronin.api.dto

import com.google.gson.annotations.SerializedName

data class DepositionPointDto(

    @SerializedName("externalId")
    val externalId: String,

    @SerializedName("partnerName")
    val partnerName: String,

    @SerializedName("location")
    val location: LocationDto,

    @SerializedName("workHours")
    val workHours: String?,

    @SerializedName("phones")
    val phones: String?,

    @SerializedName("addressInfo")
    val addressInfo: String?,

    @SerializedName("fullAddress")
    val fullAddress: String?,

    val image: ImageInfoDto = ImageInfoDto(),

)
