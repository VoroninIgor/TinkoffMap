package com.voronin.api.dto

import com.google.gson.annotations.SerializedName

data class DepositionPointDto(

    @SerializedName("partnerName")
    val partnerName : String,

    @SerializedName("location")
    val location : LocationDto,

    @SerializedName("workHours")
    val workHours : String?,

    @SerializedName("phones")
    val phones : String?,

    @SerializedName("addressInfo")
    val addressInfo : String?,

    @SerializedName("fullAddress")
    val fullAddress : String?

)
