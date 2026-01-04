package com.team.main_menu.presentation.screens.order_sheet.state

import com.team.main_menu.utils.delivery.DeliveryOffer
import java.math.BigDecimal

data class OrderSheetUiState(
    val isVisible: Boolean = false,
    val isLoading: Boolean = false,
    val offer: DeliveryOffer? = null,
    val services: List<ServiceUi> = emptyList(),
    val isPaid: Boolean = false,
    val trackingId: String? = null,
    val error: String? = null,
    val destinationCity: String = ""
) {
    val totalPrice: BigDecimal?
        get() = offer?.minPrice?.let { base ->
            base + services
                .filter { it.isSelected }
                .fold(BigDecimal.ZERO) { currentSum, element -> currentSum + element.price }
        }
}

data class ServiceUi(
    val id: String,
    val name: String,
    val price: BigDecimal,
    val isSelected: Boolean
)