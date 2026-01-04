package com.teils.database.data.room.entities.citysearchhistory

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface CitySearchHistoryDao {

    @Query("SELECT * FROM city_search_history ORDER BY itemId DESC")
    suspend fun getAll(): List<CitySearchHistoryEntity>

    @Query("DELETE FROM city_search_history WHERE itemId IN (SELECT itemId FROM city_search_history ORDER BY itemId ASC LIMIT 1)")
    suspend fun deleteOldest()

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(history: CitySearchHistoryEntity)
}