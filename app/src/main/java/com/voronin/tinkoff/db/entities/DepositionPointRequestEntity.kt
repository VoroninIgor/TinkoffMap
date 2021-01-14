package com.voronin.tinkoff.db.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.voronin.tinkoff.db.dao.DepositionPointRequestDao

@Entity(
    tableName = DepositionPointRequestDao.TABLE_NAME,
)
data class DepositionPointRequestEntity(

    @PrimaryKey(autoGenerate = true)
    val id: Int,

    @ColumnInfo(name = "latitude")
    val latitude: Double,

    @ColumnInfo(name = "longitude")
    val longitude: Double,

    @ColumnInfo(name = "radius")
    val radius: Int,

    @ColumnInfo(name = "timestamp")
    val timestamp: Long,

)
