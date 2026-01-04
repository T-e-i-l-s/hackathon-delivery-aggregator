package com.team.main_menu.presentation.screens.order_sheet.views

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.WindowInsetsSides
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.only
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.team.uikit.presentation.buttons.ProdUiButton
import com.team.uikit.presentation.text.ProdUiText

@Composable
fun SuccessCard(
    trackingId: String,
    onTrack: () -> Unit
) {
    val clipboardManager = LocalClipboardManager.current

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 24.dp)
            .padding(bottom = 24.dp)
            .windowInsetsPadding(WindowInsets.systemBars.only(WindowInsetsSides.Bottom)),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        ProdUiText(
            text = "Заказ оформлен",
            style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold)
        )

        Spacer(Modifier.height(32.dp))

        ProdUiText(
            text = "Трек-номер",
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )

        Spacer(Modifier.height(8.dp))

        ProdUiText(
            modifier = Modifier.clickable(
                onClick = {
                    clipboardManager.setText(AnnotatedString(trackingId))
                }
            ),
            text = trackingId,
            style = MaterialTheme.typography.titleLarge,
            color = MaterialTheme.colorScheme.primary
        )

        Spacer(Modifier.height(32.dp))

        ProdUiButton(
            text = "Отследить",
            onCLick = onTrack,
            modifier = Modifier.fillMaxWidth()
        )
    }
}