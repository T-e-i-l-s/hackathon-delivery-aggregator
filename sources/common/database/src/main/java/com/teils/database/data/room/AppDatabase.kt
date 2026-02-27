package com.teils.database.data.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.teils.database.data.room.entities.citysearchhistory.CitySearchHistoryDao
import com.teils.database.data.room.entities.citysearchhistory.CitySearchHistoryEntity
import com.teils.database.data.room.entities.order.OrderDao
import com.teils.database.data.room.entities.order.OrderEntity

@Database(
    entities = [CitySearchHistoryEntity::class, OrderEntity::class],
    version = 2,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun citySearchHistoryDao(): CitySearchHistoryDao
    abstract fun orderDao(): OrderDao
}