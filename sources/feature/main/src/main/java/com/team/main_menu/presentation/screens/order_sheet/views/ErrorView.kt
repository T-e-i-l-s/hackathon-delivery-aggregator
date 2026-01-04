package com.team.main_menu.presentation.screens.order_sheet.views

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.team.main_menu.R
import com.team.uikit.presentation.buttons.ProdUiButton
import com.team.uikit.presentation.text.ProdUiText

@Composable
fun ErrorView(
    onClose: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(24.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        ProdUiText(
            text = "Произошла ошибка, попробуйте снова",
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.error
        )
        ProdUiButton(
            text = stringResource(R.string.delivery_order_cta),
            onCLick = onClose
        )
    }
}