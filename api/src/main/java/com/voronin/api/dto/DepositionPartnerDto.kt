package com.voronin.api.dto

import com.google.gson.annotations.SerializedName

data class DepositionPartnerDto(

    @SerializedName("id")
    val id: String,

    @SerializedName("name")
    val name: String,

    @SerializedName("picture")
    val picture: String,

)
