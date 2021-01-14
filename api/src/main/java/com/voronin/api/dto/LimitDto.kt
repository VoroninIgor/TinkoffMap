package com.voronin.api.dto

import com.google.gson.annotations.SerializedName

data class LimitDto(

    @SerializedName("min")
    val min: Int,

    @SerializedName("max")
    val max: Int,

    @SerializedName("currency")
    val currency: CurrencyDto,
)
