package com.voronin.tinkoff.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.voronin.tinkoff.db.entities.RequestWithDepositionPointEntity
import com.voronin.tinkoff.db.entities.RequestWithDepositionPointView
import io.reactivex.Single

@Dao
interface RequestWithDepositionPointEntityDao {

    companion object {
        const val TABLE_NAME = "request_with_deposition_point"
    }

    @Query("SELECT * FROM ${DepositionPointRequestDao.TABLE_NAME}")
    fun getRequestWithPoints(): Single<List<RequestWithDepositionPointView>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(list: RequestWithDepositionPointEntity)

    @Query("DELETE FROM $TABLE_NAME WHERE requestId = :id")
    fun delete(id: Long)

    @Query("DELETE from $TABLE_NAME")
    fun clear()
}
