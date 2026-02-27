package com.teils.database.data.room.entities.order

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.teils.database.data.room.AppDatabaseTables

@Entity(tableName = AppDatabaseTables.ORDERS)
data class OrderEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val trackingId: String,
    val companyName: String,
    val companyLogoId: String,
    val tariff: String,
    val price: String,
    val destinationCity: String,
    val statedDuration: Int,
    val predictedDuration: Int,
    val createdAt: Long = System.currentTimeMillis()
)
