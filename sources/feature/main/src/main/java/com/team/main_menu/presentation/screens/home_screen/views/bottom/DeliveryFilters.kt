package com.team.main_menu.presentation.screens.home_screen.views.bottom

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.team.main_menu.R
import com.team.main_menu.presentation.screens.home_screen.HomeScreenViewModel.DeliveryTariffFilter
import com.team.uikit.presentation.text.ProdUiText

@Composable
fun DeliveryFilters(
    filters: List<DeliveryTariffFilter>,
    selectedTariff: String?,
    isAdvancedFilterApplied: Boolean,
    totalCount: Int,
    isLoading: Boolean,
    onAdvancedFilterClick: () -> Unit,
    onFilterClick: (String?) -> Unit
) {
    LazyRow(
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        contentPadding = PaddingValues(horizontal = 16.dp)
    ) {
        if (!isLoading) {
            item(key = "advanced") {
                FilterTriggerChip(
                    onClick = onAdvancedFilterClick,
                    filterApplied = isAdvancedFilterApplied
                )
            }

            item(key = "all") {
                if (totalCount != 0) {
                    CategoryChip(
                        text = "Все",
                        count = totalCount,
                        isSelected = selectedTariff == null,
                        isLoading = isLoading,
                        onClick = { onFilterClick(null) }
                    )
                }
            }
            items(filters, key = { it.tariff }) { filter ->
                AnimatedVisibility(
                    visible = true,
                    enter = fadeIn() + slideInHorizontally(initialOffsetX = { it / 2 }),
                    exit = fadeOut()
                ) {
                    CategoryChip(
                        text = filter.tariff,
                        count = filter.count,
                        isSelected = filter.isSelected,
                        onClick = { onFilterClick(filter.tariff) }
                    )
                }
            }
        }
    }
}

@Composable
fun CategoryChip(
    text: String,
    count: Int?,
    isSelected: Boolean,
    isLoading: Boolean = false,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .clip(CircleShape)
            .background(
                if (isSelected) MaterialTheme.colorScheme.secondary
                else MaterialTheme.colorScheme.surfaceVariant
            )
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = null,
                onClick = onClick
            )
            .padding(horizontal = 12.dp, vertical = 6.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        ProdUiText(
            text = text,
            style = MaterialTheme.typography.bodySmall,
            color = if (isSelected) MaterialTheme.colorScheme.onSecondary
            else MaterialTheme.colorScheme.onSurfaceVariant
        )

        if (isLoading) {
            Box(
                modifier = Modifier
                    .size(24.dp)
                    .clip(CircleShape)
                    .background(MaterialTheme.colorScheme.onSecondary.copy(alpha = 0.2f)),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator(
                    strokeWidth = 2.dp,
                    modifier = Modifier.size(16.dp),
                    color = MaterialTheme.colorScheme.onSecondary
                )
            }
        } else if (count != null && count > 0) {
            CountBubble(count = count, isSelected = isSelected)
        }
    }
}

@Composable
private fun FilterTriggerChip(
    filterApplied: Boolean = false,
    onClick: () -> Unit
) {
    val backgroundColor = if (filterApplied) {
        MaterialTheme.colorScheme.secondary
    } else {
        MaterialTheme.colorScheme.surfaceVariant
    }

    Row(
        modifier = Modifier
            .clip(CircleShape)
            .background(backgroundColor)
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = null,
                onClick = onClick
            )
            .padding(horizontal = 12.dp, vertical = 6.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        if (filterApplied) {
            Icon(
                painter = painterResource(R.drawable.filter_list_24px),
                contentDescription = null,
                tint = MaterialTheme.colorScheme.surface
            )
        } else {
            Icon(
                painter = painterResource(R.drawable.filter_list_off_24px),
                contentDescription = null,
                tint = Color.Gray
            )
        }
    }
}

@Composable
private fun CountBubble(
    count: Int,
    isSelected: Boolean
) {
    val scale = remember { Animatable(1f) }

    LaunchedEffect(isSelected) {
        if (isSelected) {
            scale.snapTo(1f)
            scale.animateTo(
                1.15f,
                animationSpec = tween(
                    durationMillis = 140,
                    easing = FastOutSlowInEasing
                )
            )
            scale.animateTo(
                1f,
                animationSpec = tween(
                    durationMillis = 140,
                    easing = FastOutSlowInEasing
                )
            )
        } else {
            scale.animateTo(
                1f,
                animationSpec = tween(durationMillis = 120)
            )
        }
    }

    Box(
        modifier = Modifier
            .graphicsLayer(
                scaleX = scale.value,
                scaleY = scale.value
            )
            .size(24.dp)
            .clip(CircleShape)
            .background(
                if (isSelected) MaterialTheme.colorScheme.onSecondary.copy(alpha = 0.2f)
                else MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.2f)
            ),
        contentAlignment = Alignment.Center
    ) {
        ProdUiText(
            text = count.toString(),
            style = MaterialTheme.typography.bodySmall,
            color = if (isSelected) MaterialTheme.colorScheme.onSecondary
            else MaterialTheme.colorScheme.onSurfaceVariant
        )
    }
}
