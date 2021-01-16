package com.voronin.tinkoff.db.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.voronin.tinkoff.db.dao.DepositionPointDao
import com.voronin.tinkoff.presentation.depositionpoints.models.ImageInfo
import com.voronin.tinkoff.presentation.depositionpoints.models.LocationGeo

@Entity(
    tableName = DepositionPointDao.TABLE_NAME
)
data class DepositionPointEntity(

    @PrimaryKey
    val id: String,

    @ColumnInfo(name = "location")
    val location: LocationGeo,

    @ColumnInfo(name = "partnerName")
    val partnerName: String,

    @ColumnInfo(name = "workHours")
    val workHours: String,

    @ColumnInfo(name = "phones")
    val phones: String,

    @ColumnInfo(name = "addressInfo")
    val addressInfo: String,

    @ColumnInfo(name = "fullAddress")
    val fullAddress: String,

    @ColumnInfo(name = "images")
    val images: ImageInfo,

    @ColumnInfo(name = "isViewed")
    val isViewed: Boolean,

)
