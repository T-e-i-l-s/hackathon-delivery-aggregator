package com.team.main_menu.presentation.screens.offer_details.views.bottom

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.AbsoluteRoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.pluralStringResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.team.main_menu.R
import com.team.main_menu.common.helpers.formatPriceLabel
import com.team.main_menu.presentation.screens.home_screen.views.bottom.CompanyLogo
import com.team.main_menu.utils.delivery.DeliveryOffer
import com.team.uikit.presentation.text.ProdUiText

@Composable
fun OfferDetailsBottomView(
    offer: DeliveryOffer,
    routeLabel: String
) {
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
                    text = formatPriceLabel(offer, true),
                    style = MaterialTheme.typography.titleMedium.copy(
                        fontWeight = FontWeight.Bold
                    ),
                    color = MaterialTheme.colorScheme.onSurface
                )
            }
        }

        DetailCard {
            DetailInfoRow(
                icon = {
                    Icon(
                        modifier = Modifier.size(32.dp),
                        tint = MaterialTheme.colorScheme.secondary,
                        painter = painterResource(R.drawable.apartment_24px),
                        contentDescription = null
                    )
                },
                title = routeLabel,
                subtitle = "Адрес доставки",
                emphasize = true
            )
        }

        DetailCard {
            Column(
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                DetailInfoRow(
                    icon = {
                        Icon(
                            modifier = Modifier.size(32.dp),
                            tint = MaterialTheme.colorScheme.secondary,
                            painter = painterResource(R.drawable.clock),
                            contentDescription = null
                        )
                    },
                    title = pluralStringResource(
                        id = R.plurals.delivery_stated_duration,
                        count = offer.statedDuration,
                        offer.statedDuration
                    ),
                    subtitle = "Заявленное время доставки",
                    emphasize = true
                )
                DetailInfoRow(
                    icon = {
                        Icon(
                            modifier = Modifier.size(32.dp),
                            tint = MaterialTheme.colorScheme.secondary,
                            painter = painterResource(R.drawable.wand_stars_24px),
                            contentDescription = null
                        )
                    },
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
            Column(
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                ProdUiText(
                    text = "Условия",
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.onSurface
                )
                DetailInfoRow(
                    title = "Тип: ${offer.tariff}",
                    subtitle = "Подходит для большинства отправлений",
                    icon = {
                        Icon(
                            modifier = Modifier.size(32.dp),
                            tint = MaterialTheme.colorScheme.secondary,
                            painter = painterResource(R.drawable.delivery_truck_bolt_24px),
                            contentDescription = null
                        )
                    }
                )
                DetailInfoRow(
                    icon = {
                        if (offer.isEstimate) {
                            Icon(
                                modifier = Modifier.size(32.dp),
                                tint = MaterialTheme.colorScheme.secondary,
                                painter = painterResource(R.drawable.wand_stars_24px),
                                contentDescription = null
                            )
                        } else {
                            Icon(
                                modifier = Modifier.size(32.dp),
                                tint = MaterialTheme.colorScheme.secondary,
                                painter = painterResource(R.drawable.money_bag_24px),
                                contentDescription = null
                            )
                        }
                    },
                    title = if (offer.isEstimate) "Цена ориентировочная" else "Цена фиксирована",
                    subtitle = formatPriceLabel(offer)
                )
            }
        }
    }
}
