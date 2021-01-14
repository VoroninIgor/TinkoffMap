package com.voronin.tinkoff.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.voronin.tinkoff.db.entities.DepositionPointRequestEntity
import io.reactivex.Single

@Dao
interface DepositionPointRequestDao {

    companion object {
        const val TABLE_NAME = "deposition_point_cache"
    }

    @Query("SELECT * FROM $TABLE_NAME")
    fun getAll(): Single<List<DepositionPointRequestEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(vararg list: DepositionPointRequestEntity)

    @Query("DELETE from $TABLE_NAME")
    fun clear()
}
