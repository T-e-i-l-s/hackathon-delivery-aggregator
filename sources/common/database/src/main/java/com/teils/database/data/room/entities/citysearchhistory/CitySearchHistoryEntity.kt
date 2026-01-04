package com.teils.database.data.room.entities.citysearchhistory

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import com.teils.database.data.room.AppDatabaseTables

@Entity(
    tableName = AppDatabaseTables.CITY_SEARCH_HISTORY,
    indices = [Index(value = ["id"], unique = true)]
)
data class CitySearchHistoryEntity(
    @PrimaryKey(autoGenerate = true) val itemId: Int = 0,
    @ColumnInfo(name = "id") val id: Int,
    val name: String,
    val region: String,
    val minPrice: String,
)
