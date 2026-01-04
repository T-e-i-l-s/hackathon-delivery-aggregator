package com.teils.database.data.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.teils.database.data.room.entities.citysearchhistory.CitySearchHistoryDao
import com.teils.database.data.room.entities.citysearchhistory.CitySearchHistoryEntity

@Database(
    entities = [CitySearchHistoryEntity::class],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun citySearchHistoryDao(): CitySearchHistoryDao
}