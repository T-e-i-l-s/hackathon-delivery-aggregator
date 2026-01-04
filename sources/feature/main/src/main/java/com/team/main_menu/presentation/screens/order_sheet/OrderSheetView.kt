package com.team.main_menu.presentation.screens.order_sheet

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.team.main_menu.R
import com.team.main_menu.presentation.screens.order_sheet.state.OrderSheetUiState
import com.team.main_menu.presentation.screens.order_sheet.views.OfferDetailsBeforeBuy
import com.team.main_menu.presentation.screens.order_sheet.views.SuccessCard
import com.teils.error.ui.presentation.DefaultErrorView

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OrderSheetBottomSheet(
    state: OrderSheetUiState,
    onClose: () -> Unit,
    onToggleService: (String) -> Unit,
    onPay: () -> Unit,
    onTrack: (String) -> Unit
) {
    val sheetState = rememberModalBottomSheetState(
        skipPartiallyExpanded = true
    )

    if (!state.isVisible) return

    ModalBottomSheet(
        containerColor = MaterialTheme.colorScheme.background,
        onDismissRequest = onClose,
        sheetState = sheetState,
    ) {
        when {
            state.isLoading -> {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(32.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    CircularProgressIndicator(
                        modifier = Modifier.size(28.dp),
                        strokeWidth = 3.dp
                    )
                }
            }

            state.error != null && !state.isPaid -> {
                DefaultErrorView(
                    buttonText = stringResource(R.string.home_screen_close_order_bottom_sheet),
                    onRetry = onClose,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 64.dp),
                    showButton = false
                )
            }

            state.isPaid -> {
                SuccessCard(
                    trackingId = state.trackingId.orEmpty(),
                    onTrack = { state.trackingId?.let(onTrack) }
                )
            }

            state.offer != null -> {
                OfferDetailsBeforeBuy(
                    offer = state.offer,
                    state = state,
                    routeLabel = buildRouteLabel(state.destinationCity),
                    onToggleService = onToggleService,
                    onPay = onPay
                )
            }
        }
    }
}

fun buildRouteLabel(destinationCity: String): String {
    val city = destinationCity.trim()
    return if (city.isEmpty()) "Москва" else "Москва - $city"
}
