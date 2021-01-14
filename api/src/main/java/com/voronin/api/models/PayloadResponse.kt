package com.voronin.api.models

import com.google.gson.annotations.SerializedName

internal data class PayloadResponse<T>(

    @SerializedName("resultCode")
    val resultCode: String,

    @SerializedName("payload")
    val payload: T,

    @SerializedName("trackingId")
    val trackingId: String,

)
