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
    val id: Long = 0,

    @ColumnInfo(name = "latitude")
    val latitude: Double,

    @ColumnInfo(name = "longitude")
    val longitude: Double,

    @ColumnInfo(name = "radius")
    val radius: Int,

    @ColumnInfo(name = "timestamp")
    val timestamp: Long,

) {
    companion object {
        fun createInstance(latitude: Double, longitude: Double, radius: Int): DepositionPointRequestEntity {
            return DepositionPointRequestEntity(
                latitude = latitude,
                longitude = longitude,
                radius = radius,
                timestamp = System.currentTimeMillis()
            )
        }
    }
}
