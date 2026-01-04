package com.team.main_menu.utils.order

import com.team.main_menu.utils.delivery.DeliveryOffer
import java.math.BigDecimal

data class OrderDetails(
    val offer: DeliveryOffer,
    val services: List<AdditionalService>
)

data class AdditionalService(
    val id: String,
    val name: String,
    val price: BigDecimal
)
