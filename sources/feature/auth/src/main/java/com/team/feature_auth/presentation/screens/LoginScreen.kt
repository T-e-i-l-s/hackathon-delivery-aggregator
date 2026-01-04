package com.team.feature_auth.presentation.screens

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.team.feature_auth.R
import com.team.feature_auth.presentation.handleYandexAuthResult
import com.team.feature_auth.presentation.state.AuthState
import com.team.feature_auth.presentation.viewModels.AuthViewModel
import com.team.uikit.presentation.buttons.ProdUiTestAccountsButton
import com.team.uikit.presentation.modals.ChooseAccountDialog
import com.team.uikit.presentation.text.ProdUiText
import com.yandex.authsdk.YandexAuthLoginOptions
import com.yandex.authsdk.YandexAuthOptions
import com.yandex.authsdk.YandexAuthSdk

@Composable
fun LoginScreen(
    onAuthSuccess: () -> Unit,
    navigateToRegisterScreen: () -> Unit
) {
    val authViewModel: AuthViewModel = hiltViewModel()
    val context = LocalContext.current

    val authState by authViewModel.authState.collectAsStateWithLifecycle()
    val showDialog by authViewModel.showDialog.collectAsStateWithLifecycle()

    if (showDialog) {
        ChooseAccountDialog(
            title = stringResource(R.string.choose_test_accounts),
            users = listOf(
                Pair("user1@gmail.com", "Lalala212103!"),
                Pair("user2@gmail.com", "Lalala212104!"),
            ).reversed(),
            confirmButtonText = stringResource(R.string.ok_text),
            denyButtonText = stringResource(R.string.cancel_text),
            onConfirm = { pair: Pair<String, String> ->
                authViewModel.showDialog(false)
                authViewModel.onEmailChange(pair.first)
                authViewModel.onPasswordChange(pair.second)
                authViewModel.login(pair.first, pair.second)
            },
            onDeny = {
                authViewModel.showDialog(false)
            }
        )
    }

    when (authState) {
        is AuthState.Loading -> {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        }

        is AuthState.Authorized -> {
            onAuthSuccess()
        }

        is AuthState.Error -> {}

        else -> {}
    }

    val yandexSdk = YandexAuthSdk.create(YandexAuthOptions(context))
    val yandexAuthLauncher =
        rememberLauncherForActivityResult(contract = yandexSdk.contract) { result ->
            handleYandexAuthResult(result)?.let { token ->
                authViewModel.signInWithYandex(token)
            }
        }

    val onSignInWithYandexClick: () -> Unit = {
        val loginOptions = YandexAuthLoginOptions()
        yandexAuthLauncher.launch(loginOptions)
    }

    LoginScreenContent(
        authState = authState,
        authViewModel = authViewModel,
        navigateToRegisterScreen = navigateToRegisterScreen,
        onTestClick = {
            authViewModel.showDialog(true)
        }
    )
}

@Composable
private fun LoginScreenContent(
    authState: AuthState,
    authViewModel: AuthViewModel,
    navigateToRegisterScreen: () -> Unit = {},
    onTestClick: () -> Unit = {}
) {
    val context = LocalContext.current
    val focusManager = LocalFocusManager.current
    val phone by authViewModel.email.collectAsStateWithLifecycle()
    val password by authViewModel.password.collectAsStateWithLifecycle()

    var phoneError by rememberSaveable { mutableStateOf<String?>(null) }
    var passwordError by rememberSaveable { mutableStateOf<String?>(null) }

    val serverError by authViewModel.serverError.collectAsStateWithLifecycle()
    val isLoading = authState is AuthState.Loading

    LaunchedEffect(authState) {
        if (authState is AuthState.Error) {
            phoneError = null
            passwordError = null
        }
    }

    val isButtonEnabled = phone.length == 12 &&
            password.length >= 4 &&
            phoneError == null &&
            passwordError == null

    Column(
        modifier = Modifier
            .fillMaxSize()
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = null,
                onClick = { focusManager.clearFocus() }
            )
            .background(MaterialTheme.colorScheme.background)
            .verticalScroll(rememberScrollState())
            .padding(16.dp)
            .systemBarsPadding(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        ProdUiText(
            text = stringResource(R.string.sign_in_title),
            style = MaterialTheme.typography.displayMedium,
            modifier = Modifier.padding(top = 16.dp)
        )

        ProdUiText(
            text = stringResource(R.string.choose_test_accounts),
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            modifier = Modifier.padding(horizontal = 4.dp)
        )

        ProdUiTestAccountsButton(onClick = onTestClick)
    }
}