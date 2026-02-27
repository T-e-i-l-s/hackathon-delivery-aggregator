package com.team.main_menu.presentation.screens.home_screen

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.team.main_menu.R
import com.team.main_menu.common.helpers.openTracking
import com.team.main_menu.presentation.screens.home_screen.views.bottom.HomeScreenBottomView
import com.team.main_menu.presentation.screens.home_screen.views.header.HomeScreenHeaderView
import com.team.main_menu.presentation.screens.order_sheet.OrderSheetBottomSheet
import com.team.main_menu.presentation.screens.order_sheet.OrderSheetViewModel
import com.team.uikit.presentation.layouts.ProdUiShrinkingHeaderScreenLayout
import com.team.uikit.presentation.modals.ProdUiConfirmationModal
import dev.chrisbanes.haze.rememberHazeState

@Composable
fun HomeScreen(
    viewModel: HomeScreenViewModel = hiltViewModel(),
    orderSheetViewModel: OrderSheetViewModel = hiltViewModel(),
    onOfferClick: (String, String) -> Unit,
    onMyOrdersClick: () -> Unit = {},
    onAboutClick: () -> Unit = {},
    exitAccount: () -> Unit,
) {
    val deliveryState by viewModel.deliveryState.collectAsStateWithLifecycle()
    val destinationCity by viewModel.targetCity.collectAsStateWithLifecycle()
    val orderSheetState by orderSheetViewModel.state.collectAsStateWithLifecycle()
    val showExitConfirmation by viewModel.showExitConfirmation.collectAsStateWithLifecycle()
    val hazeState = rememberHazeState(blurEnabled = true)
    val context = LocalContext.current

    val focusManager = LocalFocusManager.current

    ProdUiShrinkingHeaderScreenLayout(
        modifier = Modifier
            .fillMaxSize()
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = null,
                onClick = focusManager::clearFocus
            )
            .background(MaterialTheme.colorScheme.background),
        header = { shrinked ->
            HomeScreenHeaderView(
                shrinked = shrinked,
                viewModel = viewModel,
                hazeState = hazeState,
                onMyOrdersClick = onMyOrdersClick,
                onAboutClick = onAboutClick,
                onLogOutClick = {
                    viewModel.showExitConfirmation()
                }
            )
        },
        content = { lazyListState, headerHeightPx, enabled ->
            HomeScreenBottomView(
                lazyListState = lazyListState,
                enabled = enabled,
                headerHeightPx = headerHeightPx,
                hazeState = hazeState,
                deliveryState = deliveryState,
                viewModel = viewModel,
                onCardClick = { id ->
                    onOfferClick(id, destinationCity)
                },
                onOrderClick = { offer ->
                    orderSheetViewModel.openSheet(offer.id, destinationCity)
                }
            )
        }
    )

    if (showExitConfirmation) {
        ProdUiConfirmationModal(
            title = stringResource(R.string.exit_confirmation_title),
            text = stringResource(R.string.exit_confirmation_text),
            confirmButtonText = stringResource(R.string.exit_confirmation_confirm_button),
            denyButtonText = stringResource(R.string.exit_confirmation_deny_button),
            onConfirm = {
                viewModel.confirmExit()
                exitAccount()
            },
            onDeny = viewModel::closeExitConfirmation
        )
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