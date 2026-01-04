package com.team.uikit.presentation.inputs

import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.ime
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.relocation.BringIntoViewRequester
import androidx.compose.foundation.relocation.bringIntoViewRequester
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.onFocusEvent
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import com.team.uikit.R
import com.team.uikit.presentation.text.ProdUiText
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

/**
 * # ProdUiTextField
 *
 * A customizable Material 3 text field component with optional secure input, error state, and read-only mode.
 *
 * @param value The current text value of the field.
 * @param onValueChange Callback triggered when the text value changes.
 * @param placeholder The placeholder text displayed when the field is empty.
 * @param modifier Optional [Modifier] for styling and layout customization.
 * @param isError Indicates whether the text field is in an error state (shows error border color).
 * @param isSecured If true, enables secure input mode (e.g. password field) with a visibility toggle icon.
 * @param readOnly If true, makes the text field non-editable while still displaying its content.
 * @param validationType The type of validation of this text field
 * @param onValidationStatusChanged gives info about "isValid" and "errorMessage" outside the textField
 */
@Composable
fun ProdUiTextField(
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    placeholder: String? = null,
    label: String? = null,
    error: String? = null,
    isSecured: Boolean = false,
    enabled: Boolean = true,
    singleLine: Boolean = true,
    validationType: ProdUiTextFieldValidation? = null,
    animatedSize: Boolean = true,
    shape: Shape = RoundedCornerShape(12.dp),
    onValidationStatusChanged: (ProdUiTextFieldValidationState) -> Unit = {}
) {
    var isFocused by remember { mutableStateOf(false) }
    var isSecuredContentVisible by remember { mutableStateOf(false) }
    var lastError by remember { mutableStateOf("") }
    var hasUserInteracted by rememberSaveable { mutableStateOf(value.isNotEmpty()) }

    val coroutineScope = rememberCoroutineScope()
    val focusManager = LocalFocusManager.current
    val bringIntoViewRequester = remember { BringIntoViewRequester() }

    val resolvedValidationError = validationType?.let { resolveValidationError(value, it) }
    val validationError = if (hasUserInteracted) resolvedValidationError else null
    val currentError = error ?: validationError

    val errorAlpha by animateFloatAsState(
        targetValue = if (currentError != null) 1f else 0f,
        animationSpec = tween(durationMillis = 300)
    )

    var wasImeVisible by remember { mutableStateOf(false) }

    val imeInsets = WindowInsets.ime
    val imeHeight = with(LocalDensity.current) { imeInsets.getBottom(LocalDensity.current).toDp() }


    LaunchedEffect(imeHeight) {
        if (imeHeight == 0.dp && wasImeVisible) {
            focusManager.clearFocus()
            wasImeVisible = false
        } else if (imeHeight > 0.dp) {
            bringIntoViewRequester.bringIntoView()
            wasImeVisible = true
        }
    }

    LaunchedEffect(value) {
        if (value.isNotEmpty()) {
            hasUserInteracted = true
        }
    }

    LaunchedEffect(validationType, resolvedValidationError) {
        if (validationType != null) {
            onValidationStatusChanged(
                ProdUiTextFieldValidationState(
                    isValid = resolvedValidationError == null,
                    errorMessage = resolvedValidationError
                )
            )
        }
    }

    LaunchedEffect(currentError) {
        if (currentError != null) {
            lastError = currentError
            if (wasImeVisible) {
                bringIntoViewRequester.bringIntoView()
            }
        }
    }

    Column(
        Modifier
            .then(
                if (animatedSize) Modifier.animateContentSize()
                else Modifier
            )
            .bringIntoViewRequester(bringIntoViewRequester)
    ) {
        TextField(
            value = value,
            onValueChange = { newValue ->
                if (!hasUserInteracted) {
                    hasUserInteracted = true
                }
                onValueChange(newValue)
            },
            label = label?.let {
                @Composable {
                    Text(
                        text = label,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        maxLines = 1,
                    )
                }
            },
            placeholder = placeholder?.let {
                @Composable {
                    ProdUiText(
                        text = placeholder,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        style = MaterialTheme.typography.bodyMedium,
                        maxLines = if (singleLine) 1 else Int.MAX_VALUE
                    )
                }
            },
            singleLine = singleLine,
            isError = currentError != null,
            visualTransformation = if (isSecured && !isSecuredContentVisible) PasswordVisualTransformation() else VisualTransformation.None,
            colors = TextFieldDefaults.colors(
                unfocusedTextColor = MaterialTheme.colorScheme.onSurface,
                focusedTextColor = MaterialTheme.colorScheme.onSurface,
                errorTextColor = MaterialTheme.colorScheme.onSurface,
                disabledTextColor = MaterialTheme.colorScheme.onSurface,
                cursorColor = MaterialTheme.colorScheme.primary,
                focusedContainerColor = MaterialTheme.colorScheme.surface,
                unfocusedContainerColor = MaterialTheme.colorScheme.surface,
                errorContainerColor = MaterialTheme.colorScheme.surface,
                disabledContainerColor = MaterialTheme.colorScheme.surface,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                errorIndicatorColor = Color.Transparent,
                disabledIndicatorColor = Color.Transparent,
                unfocusedPlaceholderColor = MaterialTheme.colorScheme.onSurfaceVariant,
                focusedPlaceholderColor = MaterialTheme.colorScheme.onSurfaceVariant,
                errorPlaceholderColor = MaterialTheme.colorScheme.onSurfaceVariant,
            ),
            modifier = modifier
                .onFocusEvent {
                    isFocused = it.isFocused

                    if (it.isFocused) {
                        coroutineScope.launch {
                            delay(10)
                            bringIntoViewRequester.bringIntoView()
                        }
                    }
                }
                .border(
                    if (isFocused) 2.dp else 1.dp,
                    when {
                        currentError != null -> MaterialTheme.colorScheme.error
                        isFocused -> MaterialTheme.colorScheme.primary
                        else -> Color.Gray.copy(0.5f)
                    },
                    shape = shape
                )
                .clip(shape)
                .background(MaterialTheme.colorScheme.surface),
            textStyle = MaterialTheme.typography.bodyMedium,
            shape = shape,
            trailingIcon = {
                if (isSecured) {
                    val image = if (isSecuredContentVisible) {
                        painterResource(id = R.drawable.visible)
                    } else {
                        painterResource(id = R.drawable.invisible)
                    }
                    IconButton(onClick = { isSecuredContentVisible = !isSecuredContentVisible }) {
                        Icon(
                            painter = image,
                            contentDescription = null,
                            tint = if (currentError != null) {
                                MaterialTheme.colorScheme.error
                            } else {
                                MaterialTheme.colorScheme.onSurfaceVariant
                            }
                        )
                    }
                }
            },
            keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Done),
            enabled = enabled
        )

        currentError?.let {
            ProdUiText(
                text = lastError,
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.error,
                modifier = Modifier
                    .alpha(errorAlpha)
                    .padding(top = 4.dp)
                    .padding(start = 4.dp)
                    .fillMaxWidth()
            )
        }
    }
}

@Composable
private fun resolveValidationError(
    value: String,
    validationType: ProdUiTextFieldValidation
): String? {
    return when (validationType) {
        is ProdUiTextFieldValidation.Required -> {
            if (value.isEmpty()) {
                validationType.errorMessage
                    ?: stringResource(R.string.prod_text_field_error_required)
            } else {
                null
            }
        }

        is ProdUiTextFieldValidation.Email -> {
            when {
                value.isEmpty() -> validationType.emptyError
                    ?: stringResource(R.string.prod_text_field_error_required)

                !EMAIL_REGEX.matches(value.trim()) -> validationType.invalidFormatError
                    ?: stringResource(R.string.prod_text_field_error_invalid_email)

                else -> null
            }
        }

        is ProdUiTextFieldValidation.Password -> validatePassword(value, validationType)

        is ProdUiTextFieldValidation.Custom -> validationType.validator(value)

        is ProdUiTextFieldValidation.Money -> validateMoney(value, validationType)
    }
}

@Composable
private fun validateMoney(
    value: String,
    validationType: ProdUiTextFieldValidation.Money
): String? {
    if (value.isEmpty()) {
        return validationType.emptyError
            ?: stringResource(R.string.prod_text_field_error_required)
    }

    if (value.length > validationType.maxLength) {

    }

    return null
}

@Composable
private fun validatePassword(
    value: String,
    validationType: ProdUiTextFieldValidation.Password
): String? {
    if (value.isEmpty()) {
        return validationType.emptyError
            ?: stringResource(R.string.prod_text_field_error_required)
    }

    if (value.length < validationType.minLength) {
        return validationType.minLengthError
            ?: stringResource(
                R.string.prod_text_field_error_password_length,
                validationType.minLength
            )
    }

    if (validationType.requireUppercase && value.none { it.isUpperCase() }) {
        return validationType.uppercaseError
            ?: stringResource(R.string.prod_text_field_error_password_uppercase)
    }

    if (validationType.requireLowercase && value.none { it.isLowerCase() }) {
        return validationType.lowercaseError
            ?: stringResource(R.string.prod_text_field_error_password_lowercase)
    }

    if (validationType.requireDigit && value.none { it.isDigit() }) {
        return validationType.digitError
            ?: stringResource(R.string.prod_text_field_error_password_digit)
    }

    if (validationType.requireSpecialCharacter && value.none { !it.isLetterOrDigit() }) {
        return validationType.specialCharacterError
            ?: stringResource(R.string.prod_text_field_error_password_special)
    }

    return null
}

private val EMAIL_REGEX =
    "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$".toRegex()
