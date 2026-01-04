package com.team.main_menu.presentation.screens.home_screen.views.bottom_sheets

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.WindowInsetsSides
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.only
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.RangeSlider
import androidx.compose.material3.Slider
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.team.main_menu.R
import com.team.main_menu.presentation.screens.home_screen.state.DeliveryUiState
import com.team.uikit.presentation.buttons.ProdUiButton
import com.team.uikit.presentation.text.ProdUiText
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AdvancedFilterSheet(
    deliveryState: DeliveryUiState,
    onApply: (Float, Float, Int?) -> Unit,
    onClose: () -> Unit,
    onClearFilter: () -> Unit
) {
    val prices = deliveryState.offers.map { it.minPrice.toFloat() }
    val minPrice = prices.minOrNull() ?: 0f
    val maxPrice = prices.maxOrNull() ?: 0f
    val maxDuration = deliveryState.offers.maxOfOrNull { it.predictedDuration } ?: 0

    val priceUpperBound = if (minPrice == maxPrice) minPrice + 1f else maxPrice

    var priceRange by remember(minPrice, maxPrice, deliveryState.priceFilter) {
        val start = deliveryState.priceFilter?.min?.toFloat() ?: minPrice
        val end = deliveryState.priceFilter?.max?.toFloat() ?: maxPrice

        val calculatedStart = start.coerceIn(minPrice, priceUpperBound)
        val calculatedEnd = end.coerceIn(calculatedStart, priceUpperBound)
        mutableStateOf(calculatedStart..calculatedEnd)
    }

    val maxDurationBound = maxDuration.coerceAtLeast(1)
    var maxDays by remember(maxDurationBound, deliveryState.maxDurationFilter) {
        val initialMax = deliveryState.maxDurationFilter ?: maxDurationBound
        mutableStateOf(initialMax.coerceIn(1, maxDurationBound).toFloat())
    }

    val sheetState = rememberModalBottomSheetState(
        skipPartiallyExpanded = true
    )
    val screenHeight = LocalConfiguration.current.screenHeightDp.dp
    val scope = rememberCoroutineScope()

    fun closeWith(action: () -> Unit) {
        scope.launch {
            action()
            sheetState.hide()
            onClose()
        }
    }

    ModalBottomSheet(
        sheetGesturesEnabled = false,
        onDismissRequest = onClose,
        sheetState = sheetState,
        containerColor = MaterialTheme.colorScheme.background
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .heightIn(max = screenHeight * 0.8f)
                .windowInsetsPadding(WindowInsets.systemBars.only(WindowInsetsSides.Bottom))
                .padding(horizontal = 20.dp)
                .padding(bottom = 20.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(20.dp)
        ) {
            ProdUiText(
                text = stringResource(R.string.filter),
                style = MaterialTheme.typography.titleLarge
            )

            Column(
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                ProdUiText(
                    text = stringResource(R.string.price),
                    style = MaterialTheme.typography.titleMedium
                )
                ProdUiText(
                    text = "${priceRange.start.toInt()} ₽ - ${priceRange.endInclusive.toInt()} ₽",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                RangeSlider(
                    value = priceRange,
                    onValueChange = { priceRange = it },
                    valueRange = minPrice..priceUpperBound
                )
            }

            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                ProdUiText(
                    text = stringResource(R.string.max_delivery_time),
                    style = MaterialTheme.typography.titleMedium
                )
                ProdUiText(
                    text = maxDays.toInt().toString(),
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )

                Slider(
                    value = maxDays,
                    onValueChange = { newValue ->
                        val rounded = newValue.coerceIn(1f, maxDurationBound.toFloat())
                        maxDays = rounded
                    },
                    valueRange = 1f..maxDurationBound.toFloat(),
                    steps = (maxDurationBound - 2).coerceAtLeast(0)
                )
            }

            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(24.dp)
            ) {
                ProdUiButton(
                    text = stringResource(R.string.apply),
                    modifier = Modifier.fillMaxWidth(),
                    onCLick = {
                        closeWith {
                            onApply(priceRange.start, priceRange.endInclusive, maxDays.toInt())
                        }
                    }
                )

                ProdUiText(
                    text = stringResource(R.string.clear_filters),
                    color = MaterialTheme.colorScheme.secondary,
                    style = MaterialTheme.typography.bodyMedium,
                    modifier = Modifier.clickable(
                        onClick = {
                            closeWith {
                                onClearFilter()
                            }
                        }
                    )
                )
            }
        }
    }
}
