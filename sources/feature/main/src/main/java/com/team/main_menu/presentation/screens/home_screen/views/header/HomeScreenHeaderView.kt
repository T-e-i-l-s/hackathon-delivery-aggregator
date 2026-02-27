package com.team.main_menu.presentation.screens.home_screen.views.header

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.focusable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.PopupProperties
import androidx.constraintlayout.compose.ExperimentalMotionApi
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.team.main_menu.R
import com.team.main_menu.domain.weights.WeightLimits
import com.team.main_menu.presentation.screens.home_screen.HomeScreenViewModel
import com.team.uikit.presentation.inputs.ProdUiTextField
import com.team.uikit.presentation.text.ProdUiText
import dev.chrisbanes.haze.HazeState
import dev.chrisbanes.haze.hazeEffect
import dev.chrisbanes.haze.hazeSource
import dev.chrisbanes.haze.rememberHazeState

@OptIn(ExperimentalMaterial3Api::class, ExperimentalMotionApi::class)
@Composable
fun HomeScreenHeaderView(
    shrinked: Boolean,
    viewModel: HomeScreenViewModel,
    hazeState: HazeState,
    onMyOrdersClick: () -> Unit = {},
    onAboutClick: () -> Unit = {},
    onLogOutClick: () -> Unit
) {
    val weightSelectorHazeState = rememberHazeState()

    val targetCity by viewModel.targetCity.collectAsStateWithLifecycle()
    val citySearchHistory by viewModel.citySearchHistory.collectAsStateWithLifecycle()
    val loadingSuggestions by viewModel.loadingSuggestions.collectAsStateWithLifecycle()
    val sourceSuggestions by viewModel.sourceCitySuggestions.collectAsStateWithLifecycle()
    val selectedWeightLimit by viewModel.selectedWeightLimit.collectAsStateWithLifecycle()
    val showSelectCityHint by viewModel.showSelectCityHint.collectAsStateWithLifecycle()

    val focusManager = LocalFocusManager.current

    val backgroundColor by animateColorAsState(
        if (shrinked) Color(0x48383838)
        else Color.Transparent,
        animationSpec = tween(600)
    )

    val borderRadius by animateDpAsState(
        if (shrinked) 28.dp
        else 0.dp,
        animationSpec = tween(1000)
    )

    val shrinkProgress by animateFloatAsState(
        if (shrinked) 0f else 1f
    )

    var isTextFieldFocused by remember { mutableStateOf(false) }

    LaunchedEffect(isTextFieldFocused) {
        if (!isTextFieldFocused) {
            viewModel.onSearchFocusCanceled()
        } else {
            viewModel.hideSelectCityHint()
        }
    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(bottomStart = borderRadius, bottomEnd = borderRadius))
            .hazeEffect(state = hazeState)
            .background(backgroundColor)
            .statusBarsPadding()
            .padding(vertical = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
        ) {
            Column(
                modifier = Modifier
                    .align(Alignment.Center),
                horizontalAlignment = Alignment.CenterHorizontally
            ){
                Text(
                    text = stringResource(R.string.home_screen_your_location),
                    style = MaterialTheme.typography.bodyMedium.copy(
                        fontSize = MaterialTheme.typography.bodyMedium.fontSize * shrinkProgress
                    ),
                    color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = shrinkProgress),
                )

                Spacer(Modifier.height(8.dp * shrinkProgress))

                Text(
                    text = stringResource(R.string.home_screen_city_moscow_stub),
                    style = MaterialTheme.typography.titleMedium.copy(
                        fontSize = MaterialTheme.typography.titleMedium.fontSize * shrinkProgress
                    ),
                    color = MaterialTheme.colorScheme.onBackground.copy(shrinkProgress)
                )
            }

            Row(modifier = Modifier.align(Alignment.CenterEnd)) {
                IconButton(onClick = onAboutClick) {
                    Icon(
                        painter = painterResource(R.drawable.ic_info),
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.onBackground,
                        modifier = Modifier.size(24.dp * shrinkProgress)
                    )
                }

                IconButton(onClick = onMyOrdersClick) {
                    Icon(
                        painter = painterResource(R.drawable.ic_orders),
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.onBackground,
                        modifier = Modifier.size(24.dp * shrinkProgress)
                    )
                }

                IconButton(
                    onClick = { viewModel.showExitConfirmation() },
                ) {
                    Icon(
                        painter = painterResource(R.drawable.exit),
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.onBackground,
                        modifier = Modifier.size(24.dp * shrinkProgress)
                    )
                }
            }
        }

        Spacer(Modifier.height(28.dp * shrinkProgress))

        Column(modifier = Modifier.padding(horizontal = 16.dp)) {
            val textFieldBottomCornerRadius by animateDpAsState(
                if (isTextFieldFocused) 0.dp else 16.dp
            )

            ProdUiTextField(
                value = targetCity,
                onValueChange = {
                    viewModel.updateCitySearchField(it)
                },
                label = stringResource(R.string.home_screen_destination_placeholder),
                modifier = Modifier
                    .fillMaxWidth()
                    .onFocusChanged { focusState ->
                        isTextFieldFocused = focusState.isFocused
                    },
                shape = RoundedCornerShape(
                    topStart = 16.dp,
                    topEnd = 16.dp,
                    bottomStart = textFieldBottomCornerRadius,
                    bottomEnd = textFieldBottomCornerRadius,
                ),
                animatedSize = false,
                error = if (showSelectCityHint) "Выберите город из предложенных" else null
            )

            DropdownMenu(
                expanded = isTextFieldFocused,
                onDismissRequest = viewModel::resetSourceSuggestions,
                properties = PopupProperties(
                    focusable = false,
                    clippingEnabled = false
                ),
                modifier = Modifier
                    .padding(horizontal = 16.dp)
                    .heightIn(max = 200.dp)
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(bottomStart = 16.dp, bottomEnd = 16.dp))
                    .background(MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.9f))
                    .focusable(false)
                    .hazeEffect(hazeState)
                    .hazeEffect(weightSelectorHazeState)
                    .background(MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.9f)),
                containerColor = Color.Transparent,
                shape = RoundedCornerShape(bottomStart = 16.dp, bottomEnd = 16.dp),
                shadowElevation = 0.dp,
                tonalElevation = 0.dp,
            ) {
                if (targetCity.isEmpty() && citySearchHistory.isNotEmpty()) {
                    citySearchHistory.forEachIndexed { index, city ->
                        if (index != 0) {
                            HorizontalDivider(
                                thickness = 0.5.dp,
                                color = MaterialTheme.colorScheme.onSurfaceVariant.copy(0.3f),
                                modifier = Modifier.fillMaxWidth()
                            )
                        }

                        CitiesDropdownItem(
                            city,
                            isHistory = true,
                            onClick = {
                                viewModel.setSourceCity(city)
                                focusManager.clearFocus()
                            }
                        )
                    }
                } else if (targetCity.isEmpty() && sourceSuggestions.isEmpty()) {
                    if (targetCity.isEmpty()) {
                        Text(
                            text = stringResource(R.string.home_screen_city_search_empty_input),
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.onBackground,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 16.dp, vertical = 32.dp),
                            textAlign = TextAlign.Center
                        )
                    } else {
                        Text(
                            text = stringResource(R.string.home_screen_city_search_no_suggestions),
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.onBackground,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 16.dp, vertical = 32.dp),
                            textAlign = TextAlign.Center
                        )
                    }
                } else if (targetCity.isNotEmpty() && sourceSuggestions.isEmpty()) {
                    if (loadingSuggestions) {
                        Box(
                            modifier = Modifier.fillMaxWidth(),
                            contentAlignment = Alignment.Center
                        ) {
                            CircularProgressIndicator(
                                modifier = Modifier
                                    .padding(32.dp)
                                    .size(20.dp),
                                strokeWidth = 2.dp
                            )
                        }
                    } else {
                        Text(
                            text = stringResource(R.string.home_screen_city_search_no_suggestions),
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.onBackground,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 16.dp, vertical = 32.dp),
                            textAlign = TextAlign.Center
                        )
                    }
                } else {
                    sourceSuggestions.forEachIndexed { index, city ->
                        if (index != 0) {
                            HorizontalDivider(
                                thickness = 0.5.dp,
                                color = MaterialTheme.colorScheme.onSurfaceVariant.copy(0.3f),
                                modifier = Modifier.fillMaxWidth()
                            )
                        }

                        CitiesDropdownItem(
                            city,
                            onClick = {
                                viewModel.setSourceCity(city)
                                focusManager.clearFocus()
                            }
                        )
                    }
                }
            }
        }

        Spacer(Modifier.height(24.dp))

        ProdUiText(
            text = stringResource(R.string.home_screen_weight_limit_title),
            style = MaterialTheme.typography.titleLarge.copy(
                fontSize = MaterialTheme.typography.titleLarge.fontSize * shrinkProgress
            ),
            color = MaterialTheme.colorScheme.onSurface,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
        )

        Spacer(Modifier.height(8.dp * shrinkProgress))

        LazyRow(
            modifier = Modifier
                .hazeSource(weightSelectorHazeState)
                .height(64.dp + 64.dp * shrinkProgress),
            contentPadding = PaddingValues(horizontal = 16.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            items(WeightLimits.entries) { weightSegment ->
                WeightSegmentView(
                    segment = weightSegment,
                    selected = selectedWeightLimit == weightSegment.limitKg,
                    scale = shrinkProgress,
                    onClick = { viewModel.setWeightLimit(weightSegment.limitKg) }
                )
            }
        }
    }
}
