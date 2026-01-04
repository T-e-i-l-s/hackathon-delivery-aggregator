package com.team.main_menu.presentation.screens.home_screen.views.bottom

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.luminance
import androidx.compose.ui.unit.dp
import com.team.main_menu.common.helpers.parseColorOrDefault
import com.team.main_menu.utils.delivery.DeliveryBadge
import com.team.uikit.presentation.text.ProdUiText

@Composable
fun OfferBadge(
    badge: DeliveryBadge,
    modifier: Modifier = Modifier
) {
    val color = MaterialTheme.colorScheme.secondary
    val badgeColor = remember(badge.color) {
        parseColorOrDefault(badge.color, color)
    }
    val contentColor = if (badgeColor.luminance() < 0.5f) Color.White else Color.Black

    Box(
        modifier = modifier
            .clip(RoundedCornerShape(50))
            .background(badgeColor)
            .padding(horizontal = 8.dp, vertical = 2.dp)
    ) {
        ProdUiText(
            text = badge.text,
            style = MaterialTheme.typography.bodySmall,
            color = contentColor
        )
    }
}