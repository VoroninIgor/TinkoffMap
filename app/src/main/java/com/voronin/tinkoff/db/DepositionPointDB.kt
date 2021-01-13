package com.voronin.tinkoff.db

import com.j256.ormlite.field.DatabaseField
import com.j256.ormlite.table.DatabaseTable
import com.voronin.api.dto.LocationDto

@DatabaseTable(tableName = "deposition_point")
data class DepositionPointDB(

    @DatabaseField(generatedId = true)
    val id: Long,

    @DatabaseField(columnName = "partnerName")
    val partnerName: String,

    @DatabaseField(columnName = "location")
    val location: LocationDto,

    @DatabaseField(columnName = "workHours")
    val workHours: String,

    @DatabaseField(columnName = "phones")
    val phones: String,

    @DatabaseField(columnName = "addressInfo")
    val addressInfo: String,

    @DatabaseField(columnName = "fullAddress")
    val fullAddress: String,

)
