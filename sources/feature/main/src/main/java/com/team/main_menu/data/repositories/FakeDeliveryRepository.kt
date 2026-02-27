package com.team.main_menu.data.repositories

import com.team.main_menu.domain.repositories.DeliveryRepository
import com.team.main_menu.utils.delivery.DeliveryOffer
import kotlinx.coroutines.delay
import java.math.BigDecimal
import java.math.RoundingMode

class FakeDeliveryRepository : DeliveryRepository {

    override suspend fun calculateDelivery(cityId: Int, weight: Int): Result<List<DeliveryOffer>> {
        delay(700)
        val weightMultiplier = BigDecimal("1.00") + BigDecimal(weight - 1) * BigDecimal("0.15")
        val adjusted = FakeDeliveryData.offers.map { offer ->
            offer.copy(
                minPrice = (offer.minPrice * weightMultiplier).setScale(2, RoundingMode.HALF_UP)
            )
        }
        return Result.success(adjusted)
    }

    override suspend fun getOfferById(id: String): Result<DeliveryOffer> {
        delay(400)
        val offer = FakeDeliveryData.getOfferById(id)
            ?: return Result.failure(NoSuchElementException("Offer $id not found"))
        return Result.success(offer)
    }
}
