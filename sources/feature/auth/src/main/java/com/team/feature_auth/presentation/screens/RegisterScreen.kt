package com.team.feature_auth.presentation.screens

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.team.feature_auth.R
import com.team.feature_auth.presentation.components.BigButton
import com.team.feature_auth.presentation.handleYandexAuthResult
import com.team.feature_auth.presentation.state.AuthState
import com.team.feature_auth.presentation.viewModels.AuthViewModel
import com.team.uikit.presentation.ext.debounced
import com.team.uikit.presentation.inputs.ProdUiTextField
import com.team.uikit.presentation.text.ProdUiText
import com.yandex.authsdk.YandexAuthLoginOptions
import com.yandex.authsdk.YandexAuthOptions
import com.yandex.authsdk.YandexAuthSdk

@Composable
fun RegisterScreen(
    onAuthSuccess: () -> Unit,
    navigateToLoginScreen: () -> Unit = {},
) {
    val authViewModel: AuthViewModel = hiltViewModel()
    val context = LocalContext.current

    val authState by authViewModel.authState.collectAsState()

    var avatarUri by rememberSaveable { mutableStateOf<Uri?>(null) }

    val imagePickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        avatarUri = uri
    }

    val onImageClick: () -> Unit = {
        imagePickerLauncher.launch("image/*")
    }

    val onRegisterClick: (String, String, String) -> Unit = { email, password, fullName ->
        authViewModel.register(
            email = email,
            password = password
        )
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

    LaunchedEffect(authState) {
        when (authState) {
            is AuthState.Authorized -> {
                onAuthSuccess()
            }

            else -> Unit
        }
    }

    RegisterScreenContent(
        avatarUri = avatarUri,
        onImageClick = onImageClick,
        onRegisterClick = onRegisterClick,
        navigateToLoginScreen = navigateToLoginScreen,
        onSignInWithYandexClick = onSignInWithYandexClick,
        authState = authState,
    )
}

@Composable
fun RegisterScreenContent(
    authState: AuthState,
    avatarUri: Uri?,
    onImageClick: () -> Unit,
    onSignInWithYandexClick: () -> Unit,
    onRegisterClick: (String, String, String) -> Unit,
    navigateToLoginScreen: () -> Unit = {},
    authViewModel: AuthViewModel = hiltViewModel()
) {
    val context = LocalContext.current
    val focusManager = LocalFocusManager.current

    var surname by rememberSaveable { mutableStateOf("") }
    var name by rememberSaveable { mutableStateOf("") }
    var phone by rememberSaveable { mutableStateOf("") }
    var password by rememberSaveable { mutableStateOf("") }
    var passwordVisible by rememberSaveable { mutableStateOf(false) }

    var surnameError by rememberSaveable { mutableStateOf<String?>(null) }
    var nameError by rememberSaveable { mutableStateOf<String?>(null) }
    var phoneError by rememberSaveable { mutableStateOf<String?>(null) }
    var passwordError by rememberSaveable { mutableStateOf<String?>(null) }

    val serverError by authViewModel.serverError.collectAsState()
    val isLoading = authState is AuthState.Loading

    val isRegisterButtonEnabled = name.isNotEmpty() &&
            surname.isNotEmpty() &&
            password.length >= 4 &&
            surnameError == null &&
            nameError == null &&
            phoneError == null &&
            passwordError == null

    LaunchedEffect(authState) {
        if (authState is AuthState.Error) {
            surnameError = null
            nameError = null
            phoneError = null
            passwordError = null
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = null,
                onClick = { focusManager.clearFocus() }
            )
            .background(MaterialTheme.colorScheme.background)
            .padding(16.dp)
            .systemBarsPadding(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        ProdUiText(
            text = stringResource(R.string.sign_up_title),
            style = MaterialTheme.typography.displayMedium,
            modifier = Modifier.padding(top = 16.dp)
        )

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            ProdUiTextField(
                value = phone,
                enabled = false,
                onValueChange = { it ->

                },
                placeholder = stringResource(R.string.email),
                error = phoneError,
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(8.dp))

            ProdUiTextField(
                value = password,
                enabled = false,
                onValueChange = {

                },
                placeholder = stringResource(R.string.password),
                error = passwordError,
                isSecured = true,
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(12.dp))

            if (serverError != null) {
                ProdUiText(
                    text = serverError ?: "",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.error,
                    modifier = Modifier.padding(horizontal = 4.dp)
                )
            }

            Spacer(modifier = Modifier.height(20.dp))
        }

        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            BigButton(
                onClick = { onRegisterClick(phone, password, name) },
                text = stringResource(R.string.sign_up),
                enabled = isRegisterButtonEnabled && !isLoading,
                isLoading = isLoading
            )

            Spacer(Modifier.height(16.dp))

            Row(
                modifier = Modifier
                    .clickable(
                        interactionSource = remember { MutableInteractionSource() },
                        indication = null,
                        onClick = navigateToLoginScreen.debounced()
                    )
                    .padding(8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                ProdUiText(
                    text = stringResource(R.string.already_have_account),
                    style = MaterialTheme.typography.bodySmall
                )

                Spacer(Modifier.width(4.dp))

                ProdUiText(
                    text = stringResource(R.string.sign_in_button),
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.primary,
                )
            }
        }
    }
}

@Composable
fun IsAdminSwitch(
    state: Boolean,
    onCheckedChange: () -> Unit
) {
    Row(
        verticalAlignment = Alignment.CenterVertically
    ) {
        Switch(
            checked = state,
            onCheckedChange = { onCheckedChange() }
        )

        Spacer(modifier = Modifier.width(8.dp))

        Text(
            text = stringResource(R.string.enter_as_admin),
            style = MaterialTheme.typography.bodyMedium
        )
    }
}


@Preview
@Composable
private fun RegisterScreenPreview() {
    MaterialTheme(colorScheme = darkColorScheme()) {
        Surface {
            RegisterScreenContent(
                AuthState.Unauthorized,
                null,
                {}, {}, { _, _, _ -> }
            )
        }
    }
}