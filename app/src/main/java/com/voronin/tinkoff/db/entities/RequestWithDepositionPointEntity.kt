package com.voronin.tinkoff.db.entities

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Junction
import androidx.room.Relation
import com.voronin.tinkoff.db.dao.RequestWithDepositionPointEntityDao

@Entity(
    tableName = RequestWithDepositionPointEntityDao.TABLE_NAME,
    primaryKeys = ["requestId", "pointId"]
)
data class RequestWithDepositionPointEntity(

    @ForeignKey(
        entity = DepositionPointEntity::class,
        parentColumns = ["id"],
        childColumns = ["requestId"],
        onDelete = ForeignKey.CASCADE
    )
    val requestId: Long,

    val pointId: String,
)

data class RequestWithDepositionPointView(

    @Embedded
    val request: DepositionPointRequestEntity,

    @Relation(
        parentColumn = "id",
        entity = DepositionPointEntity::class,
        entityColumn = "id",
        associateBy = Junction(
            value = RequestWithDepositionPointEntity::class,
            parentColumn = "requestId",
            entityColumn = "pointId"
        )
    )
    val points: List<DepositionPointEntity>,
)
