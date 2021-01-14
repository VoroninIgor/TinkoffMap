package com.voronin.tinkoff.db.entities

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.Junction
import androidx.room.Relation
import com.voronin.tinkoff.db.dao.RequestWithDepositionPointEntityDao

@Entity(
    tableName = RequestWithDepositionPointEntityDao.TABLE_NAME,
    primaryKeys = ["requestId", "pointId"]
)
data class RequestWithDepositionPointEntity(
    val requestId: Long,
    val pointId: Long,
)

data class RequestWithDepositionPointView(

    @Embedded
    val request: DepositionPointRequestEntity,

    @Relation(
        parentColumn = "id",
        entity = DepositionPointEntity::class,
        entityColumn = "location",
        associateBy = Junction(
            value = RequestWithDepositionPointEntity::class,
            parentColumn = "requestId",
            entityColumn = "pointId"
        )
    )
    val points: List<DepositionPointEntity>,
)
