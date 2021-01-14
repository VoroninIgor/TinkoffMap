package com.voronin.tinkoff.db.dao

import androidx.room.Dao
import androidx.room.Query
import com.voronin.tinkoff.db.entities.RequestWithDepositionPointView
import io.reactivex.Single

@Dao
interface RequestWithDepositionPointEntityDao {

    companion object {
        const val TABLE_NAME = "request_with_deposition_point"
    }

    @Query("SELECT * FROM ${DepositionPointRequestDao.TABLE_NAME}")
    fun getRequestWithPoints(): Single<List<RequestWithDepositionPointView>>
}
