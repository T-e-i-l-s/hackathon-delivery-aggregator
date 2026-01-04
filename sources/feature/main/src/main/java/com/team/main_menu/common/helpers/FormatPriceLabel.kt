package com.team.main_menu.common.helpers

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.team.main_menu.R
import com.team.main_menu.utils.delivery.DeliveryOffer

@Composable
fun formatPriceLabel(
    offer: DeliveryOffer,
    newLine: Boolean = false
): String {
    val amount = offer.minPrice.toPlainString()
    return if (offer.isEstimate) {
        stringResource(R.string.delivery_estimated_price, amount)
    } else {
        stringResource(R.string.delivery_exact_price, amount)
    }
}