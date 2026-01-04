package com.team.main_menu.data.mappers

import com.team.main_menu.data.source.network.deliveryApi.dto.DeliveryOfferDto
import com.team.main_menu.utils.delivery.DeliveryBadge
import com.team.main_menu.utils.delivery.DeliveryCompany
import com.team.main_menu.utils.delivery.DeliveryOffer
import java.math.BigDecimal
import java.math.RoundingMode

fun DeliveryOfferDto.mapToDomain(): DeliveryOffer {
    return DeliveryOffer(
        id = offerId,
        tariff = tariff,
        company = DeliveryCompany(
            name = company.name,
            logoId = company.logoId
        ),
        badge = badge?.text?.let { text ->
            DeliveryBadge(
                text = text,
                color = badge.color
            )
        },
        minPrice = (minPrice.toBigDecimalOrNull() ?: BigDecimal.ZERO)
            .setScale(2, RoundingMode.HALF_UP),
        isEstimate = isEstimate,
        statedDuration = statedDuration,
        predictedDuration = predictedDuration
    )
}
