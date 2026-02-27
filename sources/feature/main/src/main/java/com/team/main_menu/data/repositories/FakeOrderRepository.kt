package com.team.main_menu.data.repositories

import com.team.main_menu.domain.repositories.OrderRepository
import com.team.main_menu.utils.order.OrderDetails
import kotlinx.coroutines.delay

class FakeOrderRepository : OrderRepository {

    override suspend fun getOrderDetails(offerId: String): Result<OrderDetails> {
        delay(500)
        val offer = FakeDeliveryData.getOfferById(offerId)
            ?: return Result.failure(NoSuchElementException("Offer $offerId not found"))
        return Result.success(
            OrderDetails(
                offer = offer,
                services = FakeDeliveryData.services
            )
        )
    }
}
