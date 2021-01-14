package com.voronin.api.dto

import com.google.gson.annotations.SerializedName

data class DepositionPartnerDto(

    @SerializedName("id")
    val id: String,

    @SerializedName("name")
    val name: String,

    @SerializedName("picture")
    val picture: String,

    @SerializedName("url")
    val url: String,

    @SerializedName("hasLocations")
    val hasLocations: Boolean,

    @SerializedName("isMomentary")
    val isMomentary: Boolean,

    @SerializedName("depositionDuration")
    val depositionDuration: String,

    @SerializedName("limitations")
    val limitations: String,

    @SerializedName("pointType")
    val pointType: String,

    @SerializedName("externalPartnerId")
    val externalPartnerId: String,

    @SerializedName("description")
    val description: String,

    @SerializedName("moneyMin")
    val moneyMin: Int,

    @SerializedName("moneyMax")
    val moneyMax: Int,

    @SerializedName("hasPreferentialDeposition")
    val hasPreferentialDeposition: Boolean,

    @SerializedName("limits")
    val limits: List<LimitDto>,

    @SerializedName("dailyLimits")
    val dailyLimits: List<DailyLimit>,

)
