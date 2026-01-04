package com.team.main_menu.presentation.screens.offer_details.state

import com.team.main_menu.utils.delivery.DeliveryOffer

data class OfferDetailsUiState(
    val offer: DeliveryOffer? = null,
    val isLoading: Boolean = false,
    val error: String? = null
)
