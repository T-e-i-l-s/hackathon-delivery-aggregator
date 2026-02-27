package com.team.main_menu.data.repositories

import com.team.main_menu.domain.repositories.OrderHistoryRepository
import com.teils.database.data.room.entities.order.OrderDao
import com.teils.database.data.room.entities.order.OrderEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class OrderHistoryRepositoryImpl @Inject constructor(
    private val orderDao: OrderDao
) : OrderHistoryRepository {
    override fun getOrders(): Flow<List<OrderEntity>> = orderDao.getAll()
    override suspend fun saveOrder(order: OrderEntity) = orderDao.insert(order)
}
