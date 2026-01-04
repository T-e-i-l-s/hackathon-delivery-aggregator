package com.team.main_menu.presentation.screens.offer_details.views

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.team.main_menu.R
import com.team.main_menu.common.helpers.buildLogoUrl
import com.team.main_menu.presentation.screens.offer_details.views.bottom.OfferDetailsBottomView
import com.team.main_menu.presentation.screens.offer_details.views.header.OfferDetailsHeaderView
import com.team.main_menu.utils.delivery.DeliveryOffer
import com.team.uikit.presentation.buttons.ProdUiButton

@Composable
fun OfferDetailsView(
    offer: DeliveryOffer,
    routeLabel: String,
    onOrderClick: (DeliveryOffer) -> Unit
) {
    val headerUrl = remember(offer.company.logoId) {
        buildLogoUrl(offer.company.logoId)
    }

    Box(
        Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        OfferDetailsHeaderView(
            headerUrl = headerUrl,
            offer = offer
        )

        Column(modifier = Modifier.fillMaxSize()) {
            Spacer(Modifier.height(220.dp))

            OfferDetailsBottomView(
                offer = offer,
                routeLabel = routeLabel
            )
        }

        ProdUiButton(
            text = stringResource(R.string.delivery_order_cta),
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .fillMaxWidth()
                .navigationBarsPadding()
                .padding(horizontal = 16.dp, vertical = 12.dp),
            onCLick = { onOrderClick(offer) }
        )
    }
}
