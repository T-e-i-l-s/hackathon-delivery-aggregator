package com.team.auth

import android.content.SharedPreferences
import com.team.preferences.data.delegates.StringPreference
import jakarta.inject.Inject
import jakarta.inject.Singleton

@Singleton
class AuthPreferences @Inject constructor(
    prefs: SharedPreferences
) {
    var jwtToken: String? by StringPreference(prefs, KEY_JWT_TOKEN)
    var fcmToken: String? by StringPreference(prefs, KEY_FCM_TOKEN)

    fun isAuthenticated(): Boolean = !jwtToken.isNullOrEmpty()

    fun clearAuth() {
        jwtToken = null
    }

    companion object {
        private const val KEY_JWT_TOKEN = "jwt_token"
        private const val KEY_FCM_TOKEN = "fcm_token"
    }
}