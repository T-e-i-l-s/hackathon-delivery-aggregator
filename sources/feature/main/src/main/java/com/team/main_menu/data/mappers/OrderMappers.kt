package com.team.main_menu.data.mappers

import com.team.main_menu.data.source.network.deliveryApi.dto.DeliveryOfferDto
import com.team.main_menu.data.source.network.orderApi.dto.OrderDetailsDto
import com.team.main_menu.data.source.network.orderApi.dto.OrderServiceDto
import com.team.main_menu.utils.order.AdditionalService
import com.team.main_menu.utils.order.OrderDetails
import java.math.BigDecimal

fun OrderDetailsDto.toDomain(): OrderDetails {
    val offerDto = DeliveryOfferDto(
        offerId = offerId,
        tariff = tariff,
        company = company,
        badge = badge,
        minPrice = minPrice,
        isEstimate = isEstimate,
        statedDuration = statedDuration,
        predictedDuration = predictedDuration
    )
    return OrderDetails(
        offer = offerDto.mapToDomain(),
        services = services.map { it.toDomain() }
    )
}

fun OrderServiceDto.toDomain(): AdditionalService {
    return AdditionalService(
        id = id,
        name = name,
        price = price.toBigDecimalOrNull() ?: BigDecimal.ZERO
    )
}
