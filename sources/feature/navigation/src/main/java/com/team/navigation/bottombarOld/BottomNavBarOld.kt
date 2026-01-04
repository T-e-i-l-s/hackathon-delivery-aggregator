package com.team.navigation.bottombarOld

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.WindowInsetsSides
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.only
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.team.navigation.MainMenuBottomNavItems
import com.team.navigation.NavItemData
import com.team.uikit.presentation.text.ProdUiText

@Composable
fun BottomNavBarOld(
    navItems: List<NavItemData>,
    selectedItem: MainMenuBottomNavItems,
    onItemSelected: (MainMenuBottomNavItems) -> Unit,
    modifier: Modifier = Modifier
) {
    Column {
        HorizontalDivider()

        Box(
            modifier = modifier
                .fillMaxWidth()
                .windowInsetsPadding(WindowInsets.systemBars.only(WindowInsetsSides.Bottom))
                .padding(horizontal = 8.dp)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(MaterialTheme.colorScheme.background)
                    .padding(horizontal = 8.dp, vertical = 2.dp),
                horizontalArrangement = Arrangement.SpaceEvenly,
                verticalAlignment = Alignment.CenterVertically
            ) {
                navItems.forEach { navItem ->
                    BottomNavItemView(
                        navItemData = navItem,
                        isSelected = selectedItem == navItem.item,
                        onClick = { onItemSelected(navItem.item) }
                    )
                }
            }
        }
    }

}

@Composable
private fun BottomNavItemView(
    navItemData: NavItemData,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    val scale by animateFloatAsState(
        targetValue = if (isSelected) 1.1f else 1f,
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioMediumBouncy,
            stiffness = Spring.StiffnessLow
        ),
        label = "scale"
    )

    val iconColor by animateColorAsState(
        targetValue = if (isSelected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurfaceVariant,
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioNoBouncy,
            stiffness = Spring.StiffnessMedium
        ),
        label = "color"
    )

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier
            .clip(RoundedCornerShape(16.dp))
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = null,
                onClick = onClick
            )
            .padding(horizontal = 12.dp, vertical = 8.dp)
    ) {
        Icon(
            painter = painterResource(if (isSelected) navItemData.selectedIcon else navItemData.unselectedIcon),
            contentDescription = navItemData.label,
            tint = iconColor,
            modifier = Modifier
                .size(24.dp)
                .scale(scale)
        )

        Spacer(modifier = Modifier.height(4.dp))

        ProdUiText(
            text = navItemData.label,
            style = MaterialTheme.typography.labelMedium,
            color = iconColor
        )
    }
}

@Preview
@Composable
private fun BottomNavBarPreview() {
    MaterialTheme {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(MaterialTheme.colorScheme.background)
        ) {
            BottomNavBarOld(
                selectedItem = MainMenuBottomNavItems.HOME,
                onItemSelected = {},
                navItems = emptyList()
            )
        }
    }
}
