package com.team.main_menu.domain.repositories

import com.team.main_menu.utils.delivery.DeliveryOffer

interface DeliveryRepository {
    suspend fun calculateDelivery(cityId: Int, weight: Int): Result<List<DeliveryOffer>>
    suspend fun getOfferById(id: String): Result<DeliveryOffer>
}
