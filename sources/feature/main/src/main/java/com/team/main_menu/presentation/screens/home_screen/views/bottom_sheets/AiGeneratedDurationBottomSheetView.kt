package com.team.main_menu.presentation.screens.home_screen.views.bottom_sheets

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.team.main_menu.R
import com.team.main_menu.presentation.screens.home_screen.HomeScreenViewModel
import com.team.uikit.presentation.text.ProdUiText

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AiGeneratedDurationBottomSheetView(viewModel: HomeScreenViewModel) {
    val sheetState = rememberModalBottomSheetState(
        skipPartiallyExpanded = true
    )

    ModalBottomSheet(
        sheetState = sheetState,
        onDismissRequest = viewModel::hideAiGeneratedDurationBottomSheet,
        containerColor = MaterialTheme.colorScheme.background
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .navigationBarsPadding()
                .padding(32.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painter = painterResource(R.drawable.robot_hand_2),
                contentDescription = null,
                modifier = Modifier
                    .size(200.dp)
                    .clip(CircleShape)
                    .background(Color(0x337B66C9))
                    .padding(horizontal = 16.dp)
                    .padding(top = 16.dp)
            )

            Spacer(Modifier.height(32.dp))

            ProdUiText(
                text = stringResource(R.string.home_screen_ai_generated_duration_title),
                style = MaterialTheme.typography.displaySmall,
                color = MaterialTheme.colorScheme.onBackground,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(horizontal = 16.dp)
            )

            Spacer(Modifier.height(12.dp))

            ProdUiText(
                text = stringResource(R.string.home_screen_ai_generated_duration_description),
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onBackground,
                textAlign = TextAlign.Center
            )
        }
    }
}