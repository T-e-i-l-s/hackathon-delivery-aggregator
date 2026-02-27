package com.team.feature_auth.data.repository

import com.team.auth.AuthPreferences
import com.team.feature_auth.domain.repository.AuthRepository
import kotlinx.coroutines.delay

class FakeAuthRepository(
    private val authPreferences: AuthPreferences
) : AuthRepository {

    override suspend fun register(email: String, password: String): Boolean {
        delay(500)
        authPreferences.jwtToken = FAKE_JWT_TOKEN
        return true
    }

    override suspend fun login(email: String, password: String): Boolean {
        delay(500)
        authPreferences.jwtToken = FAKE_JWT_TOKEN
        return true
    }

    override suspend fun signInWithYandex(token: String): Boolean {
        delay(500)
        authPreferences.jwtToken = FAKE_JWT_TOKEN
        return true
    }

    override suspend fun sendDeviceToken() { /* no-op */ }

    override fun getFcmToken(): String = "fake-fcm-token"

    override fun saveFcmToken(token: String) {
        authPreferences.fcmToken = token
    }

    override fun saveToken(token: String) {
        authPreferences.jwtToken = token
    }

    override fun getToken(): String? = authPreferences.jwtToken

    override fun clearToken() {
        authPreferences.clearAuth()
    }

    companion object {
        private const val FAKE_JWT_TOKEN = "eyJhbGciOiJIUzI1NiJ9.bW9jay11c2Vy.fake"
    }
}
