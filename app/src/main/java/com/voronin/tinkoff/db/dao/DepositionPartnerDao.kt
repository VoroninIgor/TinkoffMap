package com.voronin.tinkoff.db.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.voronin.tinkoff.db.entities.DepositionPartnerEntity
import io.reactivex.Single

@Dao
interface DepositionPartnerDao {

    companion object {
        const val TABLE_NAME = "deposition_partner"
    }

    @Query("SELECT * FROM $TABLE_NAME")
    fun getAll(): Single<List<DepositionPartnerEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(vararg list: DepositionPartnerEntity)

    @Delete(entity = DepositionPartnerEntity::class)
    fun delete(entity: DepositionPartnerEntity)

    @Query("DELETE from $TABLE_NAME")
    fun clear()
}
