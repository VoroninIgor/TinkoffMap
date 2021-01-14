package com.voronin.tinkoff.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.voronin.tinkoff.db.AppDatabase.Companion.DATABASE_VERSION
import com.voronin.tinkoff.db.dao.DepositionPointDao
import com.voronin.tinkoff.db.entities.DepositionPointEntity

@Database(
    entities = [DepositionPointEntity::class],
    version = DATABASE_VERSION,
    exportSchema = false
)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {

    companion object {
        const val DATABASE_NAME = "tinkoff.db"
        const val DATABASE_VERSION = 1
    }

    abstract fun depositionPointDao(): DepositionPointDao
}
