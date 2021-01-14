package com.voronin.api

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
        @Query("radius") radius: Int,
    ): Single<PayloadResponse<List<DepositionPointDto>>>

//    https://api.tinkoff.ru/v1/deposition_points?latitude=55.755786&longitude=37.617633&partners=EUROSET&radius=1000 - для партнеров
//
//    https://api.tinkoff.ru/v1/deposition_points?latitude=55.755786&longitude=37.617633&radius=1000 - все
}