package com.team.uikit.presentation.inputs

/**
 * Это для [ProdUiTextField].
 */
sealed class ProdUiTextFieldValidation {

    // Обычная не пустая валиадция
    data class Required(
        val errorMessage: String? = null
    ) : ProdUiTextFieldValidation()

    // валидация email
    data class Email(
        val emptyError: String? = null,
        val invalidFormatError: String? = null
    ) : ProdUiTextFieldValidation()

    // Валидация пароля
    data class Password(
        val minLength: Int = 8,
        val requireUppercase: Boolean = true,
        val requireLowercase: Boolean = true,
        val requireDigit: Boolean = true,
        val requireSpecialCharacter: Boolean = false,
        val emptyError: String? = null,
        val minLengthError: String? = null,
        val uppercaseError: String? = null,
        val lowercaseError: String? = null,
        val digitError: String? = null,
        val specialCharacterError: String? = null
    ) : ProdUiTextFieldValidation()

    // валидация для денег
    data class Money(
        val maxLength: Long = 10,
        val emptyError: String? = null,
        val invalidFormatError: String? = null
    ) : ProdUiTextFieldValidation()

    // кастом штука
    class Custom(
        val validator: (value: String) -> String?
    ) : ProdUiTextFieldValidation()
}

data class ProdUiTextFieldValidationState(
    val isValid: Boolean,
    val errorMessage: String?
)
