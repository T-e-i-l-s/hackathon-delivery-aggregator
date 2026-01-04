package com.team.uikit.presentation.layouts

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateColorAsState
import androidx.compose.ui.graphics.Color
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectVerticalDragGestures
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex

@Composable
fun ProdUiBottomSheetLayout(
    shown: Boolean,
    onClose: () -> Unit,
    content: @Composable () -> Unit,
) {
    val backgroundColor = animateColorAsState(
        if (shown) MaterialTheme.colorScheme.background.copy(0.5f)
        else Color.Transparent,
        animationSpec = tween(700)
    )

    AnimatedVisibility(
        visible = shown,
        enter = slideInVertically(
            animationSpec = spring(
                dampingRatio = Spring.DampingRatioLowBouncy,
                stiffness = Spring.StiffnessLow
            ),
            initialOffsetY = { fullHeight -> fullHeight },
        ),
        exit = slideOutVertically(
            animationSpec = spring(
                dampingRatio = Spring.DampingRatioLowBouncy,
                stiffness = Spring.StiffnessLow
            ),
            targetOffsetY = { fullHeight -> fullHeight },
        ),
        modifier = Modifier
            .background(backgroundColor.value)
            .zIndex(100f)
    ) {
        Box(
            modifier = Modifier
                .pointerInput(Unit) {
                    detectVerticalDragGestures { change, dragAmount ->
                        if (dragAmount > -20) {
                            onClose()
                        }
                    }
                }
                .fillMaxSize()
                .clickable(
                    interactionSource = remember { MutableInteractionSource() },
                    indication = null,
                    onClick = onClose
                )
        ) {
            Box(
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .heightIn(max = 800.dp)
                    .clip(RoundedCornerShape(topStart = 32.dp, topEnd = 32.dp))
                    .background(MaterialTheme.colorScheme.background)
                    .verticalScroll(rememberScrollState())
            ) {
                content()
            }
        }
    }
}