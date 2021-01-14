package com.voronin.api.dto

import com.google.gson.annotations.SerializedName

data class DailyLimit(

    @SerializedName("currency")
    val currency: CurrencyDto,

    @SerializedName("amount")
    val amount: Int,
)
