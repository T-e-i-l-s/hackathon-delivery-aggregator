package com.team.main_menu.domain.repositories

import com.team.main_menu.utils.order.OrderDetails

interface OrderRepository {
    suspend fun getOrderDetails(offerId: String): Result<OrderDetails>
}
