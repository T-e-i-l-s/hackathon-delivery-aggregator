package com.team.main_menu.presentation.screens.order_sheet.views

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.WindowInsetsSides
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.only
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.team.main_menu.R
import com.team.main_menu.presentation.screens.order_sheet.state.OrderSheetUiState
import com.team.main_menu.utils.delivery.DeliveryOffer
import com.team.uikit.presentation.buttons.ProdUiButton

@Composable
fun OfferDetailsBeforeBuy(
    offer: DeliveryOffer,
    state: OrderSheetUiState,
    routeLabel: String,
    onToggleService: (String) -> Unit,
    onPay: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .windowInsetsPadding(WindowInsets.systemBars.only(WindowInsetsSides.Bottom))
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        OrderSummaryCard(
            offer = offer,
            routeLabel = routeLabel
        )

        AdditionalServicesCard(
            services = state.services,
            onToggle = onToggleService
        )

        val totalText = state.totalPrice?.toPlainString()?.let { "$it ₽" }
            ?: stringResource(R.string.delivery_exact_price, "")

        ProdUiButton(
            text = "Оплатить $totalText",
            modifier = Modifier.fillMaxWidth(),
            onCLick = onPay
        )
    }
}
