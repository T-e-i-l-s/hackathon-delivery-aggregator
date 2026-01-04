package com.team.main_menu.presentation.screens.offer_details

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.shape.AbsoluteRoundedCornerShape
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import com.team.main_menu.R
import com.team.main_menu.common.helpers.formatPriceLabel
import com.team.main_menu.presentation.screens.home_screen.views.bottom.CompanyLogo
import com.team.main_menu.presentation.screens.home_screen.views.bottom.OfferBadge
import com.team.main_menu.utils.delivery.DeliveryOffer
import com.team.uikit.presentation.buttons.ProdUiButton
import com.team.uikit.presentation.layouts.ProdUiCollapsingHeaderScreenLayout
import com.team.uikit.presentation.text.ProdUiText

@Composable
fun OfferDetailsScreen(
    offerId: String,
    onBack: () -> Unit,
    onOrderClick: (DeliveryOffer) -> Unit,
    viewModel: OfferDetailsViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    LaunchedEffect(offerId) {
        viewModel.loadOffer(offerId)
    }

    when {
        state.isLoading -> {
            Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator()
            }
        }

        state.error != null -> {
            Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                ProdUiText(
                    text = state.error ?: stringResource(R.string.delivery_error_generic),
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.error
                )
            }
        }

        state.offer != null -> {
            OfferDetailsView(
                offer = state.offer!!,
                onBack = onBack,
                onOrderClick = onOrderClick
            )
        }
    }
}

@Composable
fun OfferDetailsView(
    offer: DeliveryOffer,
    onBack: () -> Unit,
    onOrderClick: (DeliveryOffer) -> Unit
) {
    val headerUrl =
        "https://play-lh.googleusercontent.com/8d1M9xkNAglNV1el4q7fzGInyFT44vNpSv_6OpFZmal6ZrxPqChk4GTfv_XNhv1OrWc"

    Box(
        Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        ProdUiCollapsingHeaderScreenLayout(
            modifier = Modifier.fillMaxSize(),
            header = {
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

                    IconButton(
                        modifier = Modifier
                            .align(Alignment.TopStart)
                            .statusBarsPadding()
                            .padding(start = 16.dp, top = 8.dp)
                            .clip(CircleShape)
                            .background(MaterialTheme.colorScheme.surface.copy(alpha = 0.85f)),
                        onClick = onBack
                    ) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.onSecondary
                        )
                    }

                    offer.badge?.let {
                        OfferBadge(
                            badge = it,
                            modifier = Modifier
                                .align(Alignment.BottomStart)
                                .padding(16.dp)
                        )
                    }
                }
            },
            content = {
                Column(
                    Modifier
                        .fillMaxWidth()
                        .clip(
                            AbsoluteRoundedCornerShape(
                                topLeft = 32.dp,
                                topRight = 32.dp
                            )
                        )
                        .background(MaterialTheme.colorScheme.background)
                        .padding(horizontal = 16.dp, vertical = 20.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    DetailCard {
                        Row(
                            horizontalArrangement = Arrangement.spacedBy(12.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            CompanyLogo(
                                name = offer.company.name,
                                logoId = offer.company.logoId,
                                modifier = Modifier.size(54.dp)
                            )

                            Column(
                                verticalArrangement = Arrangement.spacedBy(4.dp)
                            ) {
                                ProdUiText(
                                    text = offer.company.name,
                                    style = MaterialTheme.typography.titleMedium,
                                    color = MaterialTheme.colorScheme.onSurface
                                )
                                ProdUiText(
                                    text = stringResource(
                                        R.string.delivery_tariff_label,
                                        offer.tariff
                                    ),
                                    style = MaterialTheme.typography.bodySmall,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant
                                )
                            }

                            Spacer(modifier = Modifier.weight(1f))

                            ProdUiText(
                                text = formatPriceLabel(offer),
                                style = MaterialTheme.typography.titleLarge.copy(
                                    fontWeight = FontWeight.Bold
                                ),
                                color = MaterialTheme.colorScheme.onSurface
                            )
                        }
                    }

                    DetailCard {
                        Column(
                            verticalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            DetailInfoRow(
                                title = stringResource(
                                    R.string.delivery_stated_duration,
                                    offer.statedDuration
                                ),
                                subtitle = "Заявленное время доставки",
                                emphasize = true
                            )
                            DetailInfoRow(
                                title = stringResource(
                                    R.string.delivery_predicted_duration,
                                    offer.predictedDuration
                                ),
                                subtitle = "Среднее по данным ИИ",
                                emphasize = false
                            )
                        }
                    }

                    DetailCard {
                        Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                            ProdUiText(
                                text = "Условия",
                                style = MaterialTheme.typography.titleMedium,
                                color = MaterialTheme.colorScheme.onSurface
                            )
                            DetailInfoRow(
                                title = "Тип: ${offer.tariff}",
                                subtitle = "Подходит для большинства отправлений"
                            )
                            DetailInfoRow(
                                title = if (offer.isEstimate) "Цена ориентировочная" else "Цена фиксирована",
                                subtitle = formatPriceLabel(offer)
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(96.dp))
                }
            }
        )

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

@Composable
private fun DetailCard(content: @Composable ColumnScope.() -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(20.dp))
            .background(MaterialTheme.colorScheme.surface)
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp),
        content = content
    )
}

@Composable
private fun DetailInfoRow(
    title: String,
    subtitle: String,
    emphasize: Boolean = false
) {
    Column(verticalArrangement = Arrangement.spacedBy(2.dp)) {
        ProdUiText(
            text = title,
            style = if (emphasize) MaterialTheme.typography.titleMedium else MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurface
        )
        ProdUiText(
            text = subtitle,
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
    }
}