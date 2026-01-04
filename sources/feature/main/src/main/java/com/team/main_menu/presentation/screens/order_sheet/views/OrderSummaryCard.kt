package com.team.main_menu.presentation.screens.order_sheet.views

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.pluralStringResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.team.main_menu.R
import com.team.main_menu.common.helpers.formatPriceLabel
import com.team.main_menu.presentation.screens.home_screen.views.bottom.CompanyLogo
import com.team.main_menu.presentation.screens.offer_details.views.bottom.DetailCard
import com.team.main_menu.presentation.screens.offer_details.views.bottom.DetailInfoRow
import com.team.main_menu.utils.delivery.DeliveryOffer
import com.team.uikit.presentation.text.ProdUiText

@Composable
fun OrderSummaryCard(
    offer: DeliveryOffer,
    routeLabel: String
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
                text = formatPriceLabel(
                    offer,
                    true
                ),
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
}
