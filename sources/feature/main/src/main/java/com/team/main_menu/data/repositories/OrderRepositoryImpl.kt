package com.team.main_menu.data.repositories

import com.team.auth.AuthPreferences
import com.team.main_menu.data.mappers.toDomain
import com.team.main_menu.data.source.network.orderApi.OrderApi
import com.team.main_menu.domain.repositories.OrderRepository
import com.team.main_menu.utils.order.OrderDetails
import javax.inject.Inject

class OrderRepositoryImpl @Inject constructor(
    private val authPreferences: AuthPreferences,
    private val orderApi: OrderApi
) : OrderRepository {
    override suspend fun getOrderDetails(offerId: String): Result<OrderDetails> {

        return runCatching {
            orderApi.getOrderDetails(
                token = "Bearer ${authPreferences.jwtToken.orEmpty()}",
                id = offerId
            ).toDomain()
        }
    }
}
