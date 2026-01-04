package com.team.main_menu.data.repositories

import com.team.auth.AuthPreferences
import com.team.main_menu.data.mappers.mapToDomain
import com.team.main_menu.data.source.network.deliveryApi.DeliveryApi
import com.team.main_menu.data.source.network.deliveryApi.dto.DeliveryCalculateRequestDto
import com.team.main_menu.domain.repositories.DeliveryRepository
import com.team.main_menu.utils.delivery.DeliveryOffer
import javax.inject.Inject

class DeliveryRepositoryImpl @Inject constructor(
    private val authPreferences: AuthPreferences,
    private val deliveryApi: DeliveryApi
) : DeliveryRepository {
    override suspend fun calculateDelivery(cityId: Int, weight: Int): Result<List<DeliveryOffer>> {
        return runCatching {
            deliveryApi.getOffers(
                token = "Bearer ${authPreferences.jwtToken.orEmpty()}",
                DeliveryCalculateRequestDto(
                    cityId = cityId,
                    weight = weight
                )
            ).map { it.mapToDomain() }
        }
    }

    override suspend fun getOfferById(id: String): Result<DeliveryOffer> {
        return runCatching {
            deliveryApi.getOfferById(
                token = "Bearer ${authPreferences.jwtToken.orEmpty()}",
                id = id
            ).mapToDomain()
        }
    }
}
