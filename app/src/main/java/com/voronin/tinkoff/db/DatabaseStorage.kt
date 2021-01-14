package com.voronin.tinkoff.db

import android.content.Context
import androidx.room.Room
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DatabaseStorage
@Inject constructor(
    context: Context,
) {

    val db: AppDatabase = Room.databaseBuilder(
        context,
        AppDatabase::class.java,
        AppDatabase.DATABASE_NAME
    ).build()

    fun clearAllTables() {
        db.depositionPointDao().clear()
    }
}
