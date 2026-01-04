package com.team.main_menu.presentation.screens.order_sheet.views

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.team.main_menu.R
import com.team.main_menu.presentation.screens.order_sheet.state.ServiceUi
import com.team.uikit.presentation.inputs.ProdUiCheckbox
import com.team.uikit.presentation.text.ProdUiText
import kotlin.collections.forEach

@Composable
fun AdditionalServicesCard(
    services: List<ServiceUi>,
    onToggle: (String) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(20.dp))
            .background(MaterialTheme.colorScheme.surface)
            .border(
                1.dp,
                MaterialTheme.colorScheme.outline.copy(alpha = 0.15f),
                RoundedCornerShape(20.dp)
            )
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        ProdUiText(
            text = stringResource(R.string.additional_services),
            style = MaterialTheme.typography.titleMedium
        )

        services.forEach { service ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable(
                        onClick = { onToggle(service.id) },
                        interactionSource = remember { MutableInteractionSource() },
                        indication = null
                    ),
                verticalAlignment = Alignment.Top,
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                ProdUiCheckbox(
                    checked = service.isSelected,
                    onCheckedChange = { onToggle(service.id) }
                )

                Column(
                    modifier = Modifier.weight(1f)
                ) {
                    ProdUiText(
                        text = service.name,
                        style = MaterialTheme.typography.bodyMedium
                    )
                    ProdUiText(
                        text = "+${service.price.toPlainString()} ₽",
                        style = MaterialTheme.typography.bodySmall,
                        color = if (service.isSelected) MaterialTheme.colorScheme.secondary else MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }
        }
    }
}