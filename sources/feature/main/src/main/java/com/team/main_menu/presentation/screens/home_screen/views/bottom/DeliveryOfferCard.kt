package com.team.main_menu.presentation.screens.home_screen.views.bottom

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.pluralStringResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import coil.compose.AsyncImage
import com.team.main_menu.R
import com.team.main_menu.common.helpers.buildLogoUrl
import com.team.main_menu.common.helpers.formatPriceLabel
import com.team.main_menu.utils.delivery.DeliveryOffer
import com.team.uikit.presentation.buttons.ProdUiTinyButton
import com.team.uikit.presentation.ext.debounced
import com.team.uikit.presentation.text.ProdUiText

@Composable
fun DeliveryOfferCard(
    offer: DeliveryOffer,
    onCardClick: (String) -> Unit,
    onOrderClick: (DeliveryOffer) -> Unit,
    onAiGeneratedPriceClick: () -> Unit,
    onAiGeneratedDurationClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier
            .fillMaxWidth()
            .clickable(onClick = { onCardClick(offer.id) }.debounced())
    ) {
        Column(
            modifier = Modifier
                .padding(top = 12.dp)
                .fillMaxWidth()
                .shadow(
                    elevation = 12.dp,
                    shape = RoundedCornerShape(24.dp),
                    clip = false,
                )
                .clip(RoundedCornerShape(24.dp))
                .background(MaterialTheme.colorScheme.surface)
                .border(
                    width = 1.dp,
                    color = MaterialTheme.colorScheme.outline.copy(alpha = 0.2f),
                    shape = RoundedCornerShape(24.dp)
                )
                .padding(top = 8.dp)
                .padding(16.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                CompanyLogo(
                    name = offer.company.name,
                    logoId = offer.company.logoId,
                    modifier = Modifier.size(56.dp)
                )

                Column(
                    horizontalAlignment = Alignment.End,
                    modifier = if (offer.isEstimate) {
                        Modifier.clickable(
                            interactionSource = remember { MutableInteractionSource() },
                            indication = null,
                            onClick = onAiGeneratedPriceClick
                        )
                    } else {
                        Modifier
                    }
                ) {
                    Row(
                        modifier = if (offer.isEstimate) {
                            Modifier
                                .clip(CircleShape)
                                .background(
                                    Brush.horizontalGradient(
                                        colors = listOf(
                                            Color(0xFF4608B6),
                                            Color(0xFF6636E3),
                                            Color(0xFF6F1DB6)
                                        )
                                    )
                                )
                                .padding(horizontal = 8.dp, vertical = 2.dp)
                        } else {
                            Modifier
                        },
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        ProdUiText(
                            text = formatPriceLabel(offer),
                            style = MaterialTheme.typography.titleMedium,
                            color = MaterialTheme.colorScheme.onSecondary,
                        )
                        if (offer.isEstimate) {
                            Spacer(Modifier.width(8.dp))

                            Icon(
                                painter = painterResource(R.drawable.artificial_intelligence),
                                contentDescription = null,
                                modifier = Modifier.size(16.dp),
                                tint = MaterialTheme.colorScheme.onSecondary
                            )
                        }
                    }

                    if (offer.isEstimate) {
                        Spacer(Modifier.height(4.dp))

                        ProdUiText(
                            text = stringResource(R.string.delivery_estimated_price_hint),
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                        )
                    }
                }
            }

            Spacer(Modifier.height(16.dp))

            Row(
                verticalAlignment = Alignment.Bottom,
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {
                Column {
                    ProdUiText(
                        text = pluralStringResource(
                            id = R.plurals.delivery_stated_duration,
                            count = offer.statedDuration,
                            offer.statedDuration
                        ),
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurface,
                    )

                    Spacer(Modifier.height(8.dp))

                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            painter = painterResource(R.drawable.artificial_intelligence),
                            contentDescription = null,
                            modifier = Modifier.size(20.dp),
                            tint = Color(0xFF6636E3)
                        )

                        Spacer(Modifier.width(8.dp))

                        ProdUiText(
                            text = pluralStringResource(
                                id = R.plurals.delivery_predicted_duration,
                                count = offer.predictedDuration,
                                offer.predictedDuration
                            ),
                            style = MaterialTheme.typography.bodySmall.copy(
                                brush = Brush.horizontalGradient(
                                    colors = listOf(
                                        colorResource(R.color.ai_gradient_color_1),
                                        colorResource(R.color.ai_gradient_color_2),
                                        colorResource(R.color.ai_gradient_color_3),
                                    )
                                ),
                            ),
                            color = MaterialTheme.colorScheme.onSurface,
                            modifier = Modifier.clickable(
                                interactionSource = remember { MutableInteractionSource() },
                                indication = null,
                                onClick = onAiGeneratedDurationClick
                            )
                        )
                    }
                }

                ProdUiTinyButton(
                    text = stringResource(R.string.home_screen_order_button),
                    onClick = { onOrderClick(offer) }
                )
            }
        }

        Row(Modifier.align(Alignment.TopStart)) {
            offer.badge?.let {
                OfferBadge(
                    badge = it,
                    modifier = Modifier
                        .padding(start = 16.dp)
                        .zIndex(1f)
                )
            }

            Box(
                modifier = modifier
                    .clip(RoundedCornerShape(50))
                    .background(Color(0xFF565656))
                    .padding(horizontal = 8.dp, vertical = 2.dp)
            ) {
                ProdUiText(
                    text = stringResource(
                        id = R.string.home_screen_tariff,
                        offer.tariff
                    ),
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurface,
                )
            }
        }
    }
}


@Composable
fun CompanyLogo(
    name: String,
    logoId: String,
    modifier: Modifier = Modifier
) {
    val initials = remember(name) { name.take(2).uppercase() }
    val logoUrl = remember(logoId) {
        buildLogoUrl(logoId)
    }
    var loadFailed by remember(logoUrl) { mutableStateOf(false) }

    Box(
        modifier = modifier
            .clip(CircleShape)
            .background(MaterialTheme.colorScheme.surfaceVariant),
        contentAlignment = Alignment.Center
    ) {
        if (logoUrl.isNotBlank() && !loadFailed) {
            AsyncImage(
                model = logoUrl,
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier.matchParentSize(),
                onError = { loadFailed = true }
            )
        }

        if (logoUrl.isBlank() || loadFailed) {
            ProdUiText(
                text = initials,
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}