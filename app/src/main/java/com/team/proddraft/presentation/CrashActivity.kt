package com.team.proddraft.presentation

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.team.proddraft.R
import com.team.uikit.presentation.buttons.ProdUiButton
import com.team.uikit.presentation.text.ProdUiText
import com.team.uikit.presentation.theme.ProdUiTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CrashActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.fade_out)
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ProdUiTheme {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(MaterialTheme.colorScheme.background)
                        .padding(32.dp),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Image(
                        painter = painterResource(R.drawable.misha_crash),
                        contentDescription = null,
                        modifier = Modifier
                            .fillMaxWidth()
                            .border(
                                2.dp,
                                MaterialTheme.colorScheme.onSurface.copy(0.25f),
                                RoundedCornerShape(16.dp)
                            )
                            .clip(RoundedCornerShape(16.dp)),
                        contentScale = ContentScale.FillWidth
                    )

                    Spacer(Modifier.height(32.dp))

                    ProdUiText(
                        text = stringResource(R.string.crash_title),
                        style = MaterialTheme.typography.displaySmall,
                        color = MaterialTheme.colorScheme.onBackground
                    )

                    Spacer(Modifier.height(8.dp))

                    ProdUiText(
                        text = stringResource(R.string.crash_description),
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )

                    Spacer(Modifier.height(32.dp))

                    ProdUiButton(
                        text = stringResource(R.string.crash_button_text)
                    ) {
                        val intent = Intent(this@CrashActivity, MainActivity::class.java)
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
                        startActivity(intent)
                        finish()
                    }
                }
            }
        }
    }
}