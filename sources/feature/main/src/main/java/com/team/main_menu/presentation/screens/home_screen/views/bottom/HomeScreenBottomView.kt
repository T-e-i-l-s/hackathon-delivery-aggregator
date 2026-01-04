package com.team.main_menu.presentation.screens.home_screen.views.bottom

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.team.main_menu.presentation.screens.home_screen.HomeScreenViewModel
import com.team.main_menu.presentation.screens.home_screen.state.DeliveryUiState
import com.team.main_menu.presentation.screens.home_screen.views.bottom_sheets.AdvancedFilterSheet
import com.team.main_menu.presentation.screens.home_screen.views.bottom_sheets.AiGeneratedDurationBottomSheetView
import com.team.main_menu.presentation.screens.home_screen.views.bottom_sheets.AiGeneratedPriceBottomSheetView
import com.team.main_menu.utils.delivery.DeliveryOffer
import dev.chrisbanes.haze.HazeState
import dev.chrisbanes.haze.hazeSource

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreenBottomView(
    lazyListState: LazyListState,
    enabled: Boolean,
    headerHeightPx: Int,
    hazeState: HazeState,
    deliveryState: DeliveryUiState,
    viewModel: HomeScreenViewModel,
    onCardClick: (String) -> Unit,
    onOrderClick: (DeliveryOffer) -> Unit
) {
    val showAiGeneratedPriceBottomSheet
            by viewModel.showAiGeneratedPriceBottomSheet.collectAsStateWithLifecycle()
    val showAiGeneratedDurationBottomSheet
            by viewModel.showAiGeneratedDurationBottomSheet.collectAsStateWithLifecycle()

    var showAdvancedFilter by remember { mutableStateOf(false) }

    val isAdvancedFilterApplied = deliveryState.maxDurationFilter != null || deliveryState.priceFilter != null

    LazyColumn(
        modifier = Modifier
            .hazeSource(state = hazeState)
            .fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        contentPadding = PaddingValues(vertical = 16.dp),
        state = lazyListState,
        userScrollEnabled = enabled,
        overscrollEffect = null
    ) {
        item {
            Spacer(Modifier.height(with(LocalDensity.current) { headerHeightPx.toDp() }))

            if (deliveryState.error == null && (deliveryState.isLoading || deliveryState.offers.isNotEmpty())) {
                DeliveryFilters(
                    filters = deliveryState.filters,
                    selectedTariff = deliveryState.selectedTariff,
                    totalCount = deliveryState.filteredOffers.size,
                    isLoading = deliveryState.isLoading,
                    onAdvancedFilterClick = { showAdvancedFilter = true },
                    onFilterClick = viewModel::selectTariff,
                    isAdvancedFilterApplied = isAdvancedFilterApplied
                )
            }
        }

        deliveryOffersList(
            state = deliveryState,
            viewModel = viewModel,
            onOrderClick = onOrderClick,
            onCardClick = { offerId ->
                onCardClick(offerId)
            },
            onAiGeneratedPriceClick = viewModel::showAiGeneratedPriceBottomSheet,
            onAiGeneratedDurationClick = viewModel::showAiGeneratedDurationBottomSheet,
            onRefresh = {
                viewModel.loadOffers()
            },
            onClearFilters = {
                viewModel.clearAdvancedFilter()
            }
        )

        item {
            Spacer(Modifier.navigationBarsPadding())
        }
    }

    if (showAiGeneratedPriceBottomSheet) {
        AiGeneratedPriceBottomSheetView(viewModel)
    }

    if (showAiGeneratedDurationBottomSheet) {
        AiGeneratedDurationBottomSheetView(viewModel)
    }

    if (showAdvancedFilter && deliveryState.offers.isNotEmpty()) {
        AdvancedFilterSheet(
            deliveryState = deliveryState,
            onApply = { minPrice, maxPrice, maxDays ->
                viewModel.applyAdvancedFilter(minPrice, maxPrice, maxDays)
            },
            onClose = { showAdvancedFilter = false },
            onClearFilter = {
                viewModel.clearAdvancedFilter()
            }
        )
    }
}
