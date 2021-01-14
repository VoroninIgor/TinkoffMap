package com.voronin.api.dto

import com.google.gson.annotations.SerializedName

data class CurrencyDto(

    @SerializedName("code")
    val code: Int,

    @SerializedName("name")
    val name: String,

    @SerializedName("strCode")
    val strCode: String,

)
