package com.team.feature_auth.presentation.viewModels

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.team.auth.AuthPreferences
import com.team.feature_auth.domain.repository.AuthRepository
import com.team.feature_auth.presentation.state.AuthState
import com.team.uikit.presentation.vibration.ProdUiVibrator
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

private const val TAG = "INFOGALL"

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val prodUiVibrator: ProdUiVibrator,
    private val authRepository: AuthRepository,
    private val authPreferences: AuthPreferences
) : ViewModel() {
    private val _authState = MutableStateFlow<AuthState>(AuthState.Unauthorized)
    val authState: StateFlow<AuthState> = _authState

    private val _showDialog = MutableStateFlow(false)
    val showDialog: StateFlow<Boolean> = _showDialog

    private val _email = MutableStateFlow("")
    val email: StateFlow<String> = _email

    private val _password = MutableStateFlow("")
    val password: StateFlow<String> = _password

    private val _serverError = MutableStateFlow<String?>(null)
    val serverError: StateFlow<String?> = _serverError


    init {
        checkAuthentication()
    }

    fun showDialog(state: Boolean) {
        _showDialog.value = state
        prodUiVibrator.shortSingleVibration()
    }

    fun onEmailChange(input: String) {
        _serverError.value = null
        _email.value = input
    }

    fun onPasswordChange(value: String) {
        _serverError.value = null
        _password.value = value
    }

    fun onFocusPhone(isFocused: Boolean) {
        if (isFocused && _email.value.isEmpty()) {
            _email.value = "+7"
        }
    }

    fun checkAuthentication() {
        _authState.value = if (authPreferences.isAuthenticated()) {
            AuthState.Authorized
        } else {
            AuthState.Unauthorized
        }
    }

    fun login(email: String, password: String) {
        viewModelScope.launch {
            handleAuthAction(isLoginAttempt = true) {
                authRepository.login(email, password)
            }
        }
    }

    fun register(email: String, password: String) {
        viewModelScope.launch {
            handleAuthAction(isLoginAttempt = false) {
                authRepository.register(email, password)
            }
        }
    }

    fun signInWithYandex(token: String) {
        viewModelScope.launch {
            handleAuthAction(isLoginAttempt = true) {
                authRepository.signInWithYandex(token)
            }
        }
    }

    private suspend fun handleAuthAction(isLoginAttempt: Boolean, action: suspend () -> Boolean) {
        _authState.value = AuthState.Loading
        _serverError.value = null

        try {
            val success = action()
            if (success) {
                prodUiVibrator.shortSingleVibration()
                _authState.value = AuthState.Authorized
            } else {
                prodUiVibrator.longVibration(300)
                _authState.value =
                    AuthState.Error("Authentication failed", isLoginError = isLoginAttempt)
                _serverError.value =
                    if (isLoginAttempt) "Неверный логин или пароль" else "Что-то пошло не так, возможно пользователь с таким номером уже существует"
            }
        } catch (e: Exception) {
            Log.e(TAG, "Authentication error", e)
            prodUiVibrator.shortDoubleVibration()
            val isUserExists = (e as? retrofit2.HttpException)?.code() == 409
            val errorMessage = when {
                isUserExists -> "Пользователь с таким номером телефона уже зарегистрирован"
                e is retrofit2.HttpException && (e.code() == 400 || e.code() == 401) -> "Неверный логин или пароль"
                else -> "Что-то пошло не так. Попробуйте позже"
            }
            _authState.value = AuthState.Error(
                errorMessage,
                isLoginError = isLoginAttempt,
            )
            _serverError.value = errorMessage
        }
    }

    fun logout() {
        authRepository.clearToken()
        _authState.value = AuthState.Unauthorized
        _serverError.value = null
    }

    fun isAuthenticated(): Boolean = authPreferences.isAuthenticated()
}
