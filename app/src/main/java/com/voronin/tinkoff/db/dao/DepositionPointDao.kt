package com.voronin.tinkoff.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.voronin.tinkoff.db.entities.DepositionPointEntity
import io.reactivex.Single

@Dao
interface DepositionPointDao {

    companion object {
        const val TABLE_NAME = "deposition_point"
    }

    @Query("SELECT * FROM $TABLE_NAME")
    fun getAll(): Single<List<DepositionPointEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(vararg list: DepositionPointEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(item: DepositionPointEntity): Long

    @Query("DELETE from $TABLE_NAME")
    fun clear()
}
