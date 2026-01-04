package com.team.main_menu.presentation.screens.offer_details

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.team.main_menu.common.helpers.openTracking
import com.team.main_menu.presentation.screens.offer_details.views.OfferDetailsView
import com.team.main_menu.presentation.screens.order_sheet.OrderSheetBottomSheet
import com.team.main_menu.presentation.screens.order_sheet.OrderSheetViewModel
import com.team.main_menu.presentation.screens.order_sheet.buildRouteLabel
import com.team.uikit.presentation.ext.debounced
import com.teils.error.ui.presentation.DefaultErrorView

@Composable
fun OfferDetailsScreen(
    offerId: String,
    destinationCity: String,
    onBack: () -> Unit,
    viewModel: OfferDetailsViewModel = hiltViewModel(),
    orderSheetViewModel: OrderSheetViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    val orderSheetState by orderSheetViewModel.state.collectAsStateWithLifecycle()
    val context = LocalContext.current
    val routeLabel = buildRouteLabel(destinationCity)

    LaunchedEffect(offerId) {
        viewModel.loadOffer(offerId)
    }

    Box(Modifier.fillMaxSize()) {
        IconButton(
            modifier = Modifier
                .align(Alignment.TopStart)
                .zIndex(1f)
                .statusBarsPadding()
                .padding(start = 16.dp, top = 8.dp)
                .clip(CircleShape)
                .background(MaterialTheme.colorScheme.surface.copy(alpha = 0.85f)),
            onClick = onBack.debounced()
        ) {
            Icon(
                imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.secondary
            )
        }

        when {
            state.isLoading -> {
                Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator(
                        modifier = Modifier.size(28.dp),
                        strokeWidth = 3.dp
                    )
                }
            }

            state.error != null -> {
                DefaultErrorView(
                    onRetry = { viewModel.loadOffer(offerId) },
                    modifier = Modifier.fillMaxSize()
                )
            }

            state.offer != null -> {
                OfferDetailsView(
                    offer = state.offer!!,
                    routeLabel = routeLabel,
                    onOrderClick = { offer ->
                        orderSheetViewModel.openSheet(offer.id, destinationCity)
                    }
                )
            }
        }
    }

    OrderSheetBottomSheet(
        state = orderSheetState,
        onClose = orderSheetViewModel::close,
        onToggleService = orderSheetViewModel::toggleService,
        onPay = orderSheetViewModel::pay,
        onTrack = { trackingId ->
            openTracking(context, trackingId)
        }
    )
}
