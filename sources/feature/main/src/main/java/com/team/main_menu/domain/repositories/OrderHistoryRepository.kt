package com.team.main_menu.domain.repositories

import com.teils.database.data.room.entities.order.OrderEntity
import kotlinx.coroutines.flow.Flow

interface OrderHistoryRepository {
    fun getOrders(): Flow<List<OrderEntity>>
    suspend fun saveOrder(order: OrderEntity)
}
