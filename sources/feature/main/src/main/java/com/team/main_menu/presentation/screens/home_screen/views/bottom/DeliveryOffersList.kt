package com.team.main_menu.presentation.screens.home_screen.views.bottom

import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.team.main_menu.R
import com.team.main_menu.presentation.screens.home_screen.HomeScreenViewModel
import com.team.main_menu.presentation.screens.home_screen.state.DeliveryUiState
import com.team.main_menu.utils.delivery.DeliveryOffer
import com.team.uikit.presentation.buttons.ProdUiTinyButton
import com.team.uikit.presentation.text.ProdUiText

@OptIn(ExperimentalFoundationApi::class)
fun LazyListScope.deliveryOffersList(
    state: DeliveryUiState,
    viewModel: HomeScreenViewModel,
    onOrderClick: (DeliveryOffer) -> Unit,
    onCardClick: (String) -> Unit,
    onRefresh: () -> Unit,
    onClearFilters: () -> Unit,
    onAiGeneratedPriceClick: () -> Unit,
    onAiGeneratedDurationClick: () -> Unit,
) {
    when {
        state.error == null && !state.isLoading && state.filteredOffers.isEmpty() && (state.priceFilter != null || state.maxDurationFilter != null) -> {
            item {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(180.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Column(
                        verticalArrangement = Arrangement.spacedBy(8.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        ProdUiText(
                            textAlign = TextAlign.Center,
                            text = stringResource(R.string.no_offers_available),
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                            modifier = Modifier
                                .padding(horizontal = 32.dp)
                        )

                        ProdUiText(
                            textAlign = TextAlign.Center,
                            text = stringResource(R.string.clear_filters),
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.secondary,
                            modifier = Modifier
                                .padding(horizontal = 32.dp)
                                .clickable(
                                    interactionSource = remember { MutableInteractionSource() },
                                    indication = null,
                                    onClick = {
                                        onClearFilters()
                                    }
                                )
                        )
                    }
                }

                Spacer(Modifier.height(32.dp))

                DeliveryInitialSuggestions(viewModel = viewModel)
            }
        }

        state.isLoading -> {
            item {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(400.dp),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator(
                        modifier = Modifier.size(28.dp),
                        strokeWidth = 3.dp
                    )
                }
            }
        }

        state.error != null -> {
            item {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 140.dp, horizontal = 16.dp),
                    verticalArrangement = Arrangement.spacedBy(4.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    ProdUiText(
                        text = stringResource(R.string.home_screen_loading_error_title),
                        style = MaterialTheme.typography.titleMedium,
                        color = MaterialTheme.colorScheme.onBackground
                    )

                    Spacer(Modifier.height(4.dp))

                    ProdUiText(
                        text = stringResource(R.string.home_screen_loading_error_text),
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )

                    Spacer(Modifier.height(8.dp))

                    ProdUiTinyButton(
                        text = stringResource(R.string.home_screen_loading_error_button_text),
                        onClick = onRefresh,
                        containerColor = MaterialTheme.colorScheme.surfaceVariant,
                        contentColor = MaterialTheme.colorScheme.secondary
                    )
                }
            }
        }

        state.visibleOffers.isEmpty() -> {
            item {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp)
                        .clip(RoundedCornerShape(24.dp))
                        .background(MaterialTheme.colorScheme.surface)
                        .padding(32.dp),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Icon(
                        painter = painterResource(R.drawable.city),
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.onSurfaceVariant,
                        modifier = Modifier.size(80.dp)
                    )

                    Spacer(Modifier.height(8.dp))

                    ProdUiText(
                        text = stringResource(R.string.delivery_initial_state),
                        style = MaterialTheme.typography.bodyLarge,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }

                Spacer(Modifier.height(32.dp))

                DeliveryInitialSuggestions(viewModel = viewModel)

                Spacer(Modifier.height(200.dp))
            }
        }

        else -> {
            items(
                items = state.visibleOffers,
                key = { it.id }
            ) { offer ->
                DeliveryOfferCard(
                    offer = offer,
                    onCardClick = { offerId ->
                        onCardClick(offerId)
                    },
                    onOrderClick = onOrderClick,
                    onAiGeneratedPriceClick = onAiGeneratedPriceClick,
                    onAiGeneratedDurationClick = onAiGeneratedDurationClick,
                    modifier = Modifier
                        .animateItem(
                            fadeInSpec = spring(stiffness = Spring.StiffnessLow),
                            placementSpec = spring(
                                dampingRatio = Spring.DampingRatioLowBouncy,
                                stiffness = Spring.StiffnessHigh
                            ),
                            fadeOutSpec = spring(stiffness = Spring.StiffnessLow)
                        )
                        .padding(horizontal = 16.dp),
                )
            }
        }
    }
}