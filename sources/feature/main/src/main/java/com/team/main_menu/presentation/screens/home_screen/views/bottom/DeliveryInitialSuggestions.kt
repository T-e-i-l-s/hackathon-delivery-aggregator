package com.team.main_menu.presentation.screens.home_screen.views.bottom

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.team.main_menu.R
import com.team.main_menu.domain.cities.Cities
import com.team.main_menu.presentation.screens.home_screen.HomeScreenViewModel
import com.team.uikit.presentation.text.ProdUiText

@Composable
fun DeliveryInitialSuggestions(viewModel: HomeScreenViewModel) {
    val citySearchHistory by viewModel.citySearchHistory.collectAsStateWithLifecycle()

    Column(
        modifier = Modifier
            .padding(horizontal = 16.dp)
            .fillMaxWidth()
            .clip(RoundedCornerShape(24.dp))
            .background(MaterialTheme.colorScheme.surface)
    ) {
        ProdUiText(
            text = stringResource(R.string.home_screen_initial_suggestions_title),
            style = MaterialTheme.typography.displaySmall,
            color = MaterialTheme.colorScheme.onSurface,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 24.dp, vertical = 24.dp)
        )

        citySearchHistory.forEach { city ->
            HorizontalDivider(
                thickness = 0.5.dp,
                color = MaterialTheme.colorScheme.onSurfaceVariant.copy(0.3f),
                modifier = Modifier.fillMaxWidth()
            )

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { viewModel.setSourceCity(city) }
                    .padding(horizontal = 24.dp, vertical = 24.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    ProdUiText(
                        text = city.name,
                        style = MaterialTheme.typography.titleMedium,
                        color = MaterialTheme.colorScheme.onSurface
                    )

                    Spacer(Modifier.height(4.dp))

                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            painter = painterResource(R.drawable.history),
                            contentDescription = null,
                            modifier = Modifier.size(16.dp),
                            tint = MaterialTheme.colorScheme.onSurfaceVariant
                        )

                        Spacer(Modifier.width(2.dp))

                        ProdUiText(
                            text = stringResource(R.string.home_screen_recent_search),
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }


                Row(verticalAlignment = Alignment.CenterVertically) {
                    ProdUiText(
                        text = stringResource(
                            R.string.home_screen_minimum_price,
                            city.minPrice
                        ),
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurface
                    )

                    Spacer(Modifier.width(16.dp))

                    Icon(
                        painter = painterResource(R.drawable.arrow_right),
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.onSurfaceVariant,
                        modifier = Modifier.size(16.dp)
                    )
                }
            }
        }

        Cities.entries.filter { !citySearchHistory.contains(it.info) }
            .forEachIndexed { index, city ->
                HorizontalDivider(
                    thickness = 0.5.dp,
                    color = MaterialTheme.colorScheme.onSurfaceVariant.copy(0.3f),
                    modifier = Modifier.fillMaxWidth()
                )

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { viewModel.setSourceCity(city.info) }
                        .padding(horizontal = 24.dp, vertical = 24.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    ProdUiText(
                        text = city.info.name,
                        style = MaterialTheme.typography.titleMedium,
                        color = MaterialTheme.colorScheme.onSurface
                    )

                    Row(verticalAlignment = Alignment.CenterVertically) {
                        ProdUiText(
                            text = stringResource(
                                R.string.home_screen_minimum_price,
                                city.info.minPrice
                            ),
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.onSurface
                        )

                        Spacer(Modifier.width(16.dp))

                        Icon(
                            painter = painterResource(R.drawable.arrow_right),
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.onSurfaceVariant,
                            modifier = Modifier.size(16.dp)
                        )
                    }
                }
            }
    }
}