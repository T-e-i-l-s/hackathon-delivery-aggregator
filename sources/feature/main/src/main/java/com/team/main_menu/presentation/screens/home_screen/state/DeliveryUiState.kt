package com.team.main_menu.presentation.screens.home_screen.state

import com.team.main_menu.presentation.screens.home_screen.HomeScreenViewModel.DeliveryTariffFilter
import com.team.main_menu.utils.delivery.DeliveryOffer
import java.math.BigDecimal

data class DeliveryUiState(
    val offers: List<DeliveryOffer> = emptyList(),
    val selectedTariff: String? = null,
    val priceFilter: PriceFilter? = null,
    val maxDurationFilter: Int? = null,
    val isLoading: Boolean = false,
    val error: String? = null
) {
    val filters: List<DeliveryTariffFilter>
        get() = filteredOffers.groupingBy { it.tariff }
            .eachCount()
            .map { (tariff, count) ->
                DeliveryTariffFilter(
                    tariff = tariff,
                    count = count,
                    isSelected = selectedTariff == tariff
                )
            }

    val filteredOffers: List<DeliveryOffer>
        get() {
            var list = offers
            priceFilter?.let { range ->
                list = list.filter { it.minPrice in range.min..range.max }
            }
            maxDurationFilter?.let { max ->
                list = list.filter { it.predictedDuration <= max }
            }
            return list
        }

    val visibleOffers: List<DeliveryOffer>
        get() {
            var list = filteredOffers
            selectedTariff?.let { tariff ->
                list = list.filter { it.tariff == tariff }
            }
            return list
        }
}

data class PriceFilter(
    val min: BigDecimal,
    val max: BigDecimal
)
