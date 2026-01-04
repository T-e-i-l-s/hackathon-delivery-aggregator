package com.team.main_menu.presentation.screens.home_screen.views.header

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.team.main_menu.R
import com.team.main_menu.domain.weights.WeightLimits
import com.team.uikit.presentation.text.ProdUiText

@Composable
fun WeightSegmentView(
    segment: WeightLimits,
    selected: Boolean,
    scale: Float,
    onClick: () -> Unit
) {
    val gradientColor by animateColorAsState(
        if (selected) Color(0xFFB0834C) else Color.Transparent,
        animationSpec = tween(400)
    )
    val borderWidth by animateDpAsState(
        if (selected) 2.dp else 0.dp,
        animationSpec = tween(400)
    )

    Box(
        Modifier
            .fillMaxHeight()
            .width(168.dp * scale.coerceIn(0.55f, 1.0f))
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = null,
                onClick = onClick,
                enabled = !selected
            )
    ) {
        Box(
            Modifier
                .align(Alignment.BottomCenter)
                .fillMaxSize()
                .padding(top = 20.dp * scale, end = 8.dp * scale)
                .clip(RoundedCornerShape(16.dp))
                .border(
                    width = borderWidth,
                    color = gradientColor.copy(0.7f),
                    shape = RoundedCornerShape(16.dp)
                )
                .background(MaterialTheme.colorScheme.surfaceVariant)
                .background(
                    Brush.linearGradient(
                        colors = listOf(gradientColor, Color.Transparent),
                        start = Offset(x = 300f, y = -100f),
                        end = Offset(x = 250f, y = 200f)
                    )
                )
                .padding(16.dp)
        ) {
            Column(Modifier.align(Alignment.BottomStart)) {
                ProdUiText(
                    text = stringResource(R.string.home_screen_weight_limit_text, segment.limitKg),
                    style = MaterialTheme.typography.displaySmall.copy(
                        fontSize = MaterialTheme.typography.displaySmall.fontSize
                    ),
                    color = MaterialTheme.colorScheme.onSurface
                )
            }
        }

        Image(
            painter = painterResource(
                when (segment) {
                    WeightLimits.KG1 -> R.drawable.letter
                    WeightLimits.KG5 -> R.drawable.box
                    WeightLimits.KG10 -> R.drawable.box4
                    WeightLimits.KG20 -> R.drawable.box3
                    WeightLimits.KG30 -> R.drawable.box2
                }
            ),
            contentDescription = null,
            modifier = Modifier
                .align(Alignment.TopEnd)
                .height(80.dp * scale)
                .alpha(scale),
            contentScale = ContentScale.FillHeight
        )
    }
}