package com.voronin.api

import com.voronin.api.dto.DepositionPartnerDto
import com.voronin.api.dto.DepositionPointDto
import com.voronin.api.models.PayloadResponse
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

internal interface TinkoffApiService {

    @GET("deposition_points")
    fun getAllDepositionPoints(
        @Query("latitude") latitude: Double,
        @Query("longitude") longitude: Double,
        @Query("longitude") partners: String? = null,
        @Query("radius") radius: Int,
    ): Single<PayloadResponse<List<DepositionPointDto>>>

    @GET("deposition_partners")
    fun getDepositionPartners(
        @Query("accountType") accountType: String,
    ): Single<PayloadResponse<List<DepositionPartnerDto>>>
}
