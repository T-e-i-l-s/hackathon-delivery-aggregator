package com.team.uikit.presentation.buttons

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.team.uikit.R
import com.team.uikit.presentation.text.ProdUiText

@Composable
fun YandexSignInButton(onClick: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(20.dp))
            .clickable(onClick = onClick)
            .background(
                Brush.horizontalGradient(
                    listOf(
                        MaterialTheme.colorScheme.surface,
                        MaterialTheme.colorScheme.secondary.copy(0.3f)
                    )
                )
            )
            .border(
                2.dp,
                MaterialTheme.colorScheme.onSurface.copy(0.05f),
                RoundedCornerShape(20.dp)
            )
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        Icon(
            painter = painterResource(id = R.drawable.ic_yandex_logo),
            contentDescription = null,
            tint = Color.Unspecified,
            modifier = Modifier.size(28.dp)
        )

        Spacer(modifier = Modifier.width(12.dp))

        ProdUiText(
            text = stringResource(R.string.sign_in_with_yandex_id),
            style = MaterialTheme.typography.titleMedium
        )
    }
}

@Preview
@Composable
private fun YandexSignInButtonDarkPreview() {
    MaterialTheme(
        colorScheme = darkColorScheme()
    ) {
        YandexSignInButton({})
    }
}

@Preview
@Composable
private fun YandexSignInButtonLightPreview() {
    MaterialTheme(
        colorScheme = lightColorScheme()
    ) {
        YandexSignInButton({})
    }
}