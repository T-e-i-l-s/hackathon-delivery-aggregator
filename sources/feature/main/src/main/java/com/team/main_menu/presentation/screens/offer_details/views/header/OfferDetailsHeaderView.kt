package com.team.main_menu.presentation.screens.offer_details.views.header

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.team.main_menu.presentation.screens.home_screen.views.bottom.OfferBadge
import com.team.main_menu.utils.delivery.DeliveryOffer

@Composable
fun OfferDetailsHeaderView(
    headerUrl: String,
    offer: DeliveryOffer
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(260.dp)
    ) {
        AsyncImage(
            model = headerUrl,
            contentDescription = null,
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )

        offer.badge?.let {
            OfferBadge(
                badge = it,
                modifier = Modifier
                    .align(Alignment.BottomStart)
                    .padding(16.dp)
            )
        }
    }
}