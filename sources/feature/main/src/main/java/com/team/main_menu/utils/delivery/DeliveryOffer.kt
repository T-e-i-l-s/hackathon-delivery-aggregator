package com.team.main_menu.utils.delivery

import java.math.BigDecimal

data class DeliveryOffer(
    val id: String,
    val tariff: String,
    val company: DeliveryCompany,
    val badge: DeliveryBadge?,
    val minPrice: BigDecimal,
    val isEstimate: Boolean,
    val statedDuration: Int,
    val predictedDuration: Int
)

data class DeliveryCompany(
    val name: String,
    val logoId: String
)

data class DeliveryBadge(
    val text: String,
    val color: String
)
