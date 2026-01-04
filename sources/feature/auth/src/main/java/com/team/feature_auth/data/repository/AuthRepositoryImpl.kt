package com.team.feature_auth.data.repository

import android.content.Context
import android.net.Uri
import android.util.Log
import com.team.auth.AuthPreferences
import com.team.feature_auth.data.remote.api.AuthApi
import com.team.feature_auth.data.remote.api.dto.AuthResponse
import com.team.feature_auth.data.remote.api.dto.LoginRequestDto
import com.team.feature_auth.data.remote.api.dto.NotificationsRequestDto
import com.team.feature_auth.data.remote.api.dto.RegisterRequestDto
import com.team.feature_auth.data.remote.api.dto.YandexToken
import com.team.feature_auth.domain.repository.AuthRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.File
import java.io.FileOutputStream
import javax.inject.Inject

private const val TAG = "INFOGALL"

class AuthRepositoryImpl @Inject constructor(
    private val api: AuthApi,
    private val authPreferences: AuthPreferences,
    private val context: Context
) : AuthRepository {

    override suspend fun register(email: String, password: String): Boolean {
        return try {
            api.register(
                RegisterRequestDto(
                    email = email,
                    password = password
                )
            )

            val loginResponse = api.login(LoginRequestDto(email, password))
            val result = handleAuthResponse(loginResponse)

            trySendDeviceToken("registration")

            result
        } catch (e: Exception) {
            Log.e(TAG, "Registration failed", e)
            false
        }
    }

    override suspend fun login(email: String, password: String): Boolean {
        return try {
            val response = api.login(LoginRequestDto(email, password))
            val result = handleAuthResponse(response)

            trySendDeviceToken("login")

            result
        } catch (e: Exception) {
            Log.e(TAG, "Login failed", e)
            false
        }
    }

    override suspend fun signInWithYandex(token: String): Boolean {
        return try {
            val response = api.signInWithYandex(YandexToken(token))
            val result = handleAuthResponse(response)

            trySendDeviceToken("Yandex sign-in")

            result
        } catch (e: Exception) {
            Log.e(TAG, "Yandex sign-in failed", e)
            false
        }
    }

    private suspend fun trySendDeviceToken(operation: String) {
        try {
            sendDeviceToken()
        } catch (e: Exception) {
            Log.e(TAG, "Failed to send device token after $operation", e)
        }
    }

    override suspend fun sendDeviceToken() {
        val token = getFcmToken()
        api.sendDeviceToken(NotificationsRequestDto(token, "android"))
    }

    override fun getFcmToken(): String {
        return authPreferences.fcmToken ?: throw IllegalStateException("FCM token is null")
    }

    override fun saveFcmToken(token: String) {
        authPreferences.fcmToken = token
    }

    private fun handleAuthResponse(response: AuthResponse): Boolean {
        saveToken(response.token)
        return true
    }

    override fun saveToken(token: String) {
        authPreferences.jwtToken = token
    }

    override fun getToken(): String? {
        return authPreferences.jwtToken
    }

    override fun clearToken() {
        authPreferences.clearAuth()
    }

    private suspend fun loadImage(uri: Uri): String? = withContext(Dispatchers.IO) {
        try {
            val tempFile = createTempFileFromUri(uri) ?: return@withContext null
            return@withContext uploadFileToServer(tempFile)
        } catch (e: Exception) {
            Log.e(TAG, "Error loading image", e)
            return@withContext null
        }
    }

    private suspend fun createTempFileFromUri(uri: Uri): File? {
        return try {
            val tempFile = File.createTempFile("avatar_", ".jpg")

            context.contentResolver.openInputStream(uri)?.use { input ->
                FileOutputStream(tempFile).use { output ->
                    input.copyTo(output)
                }
            } ?: return null

            tempFile
        } catch (e: Exception) {
            Log.e(TAG, "Failed to create temp file from URI", e)
            null
        }
    }

    private suspend fun uploadFileToServer(file: File): String {
        val requestFile = file.asRequestBody("image/*".toMediaTypeOrNull())

        val multipartFile = MultipartBody.Part.createFormData(
            "file",
            file.name,
            requestFile
        )

        val response = api.uploadFile(file = multipartFile)
        return response.url
    }
}
