package com.team.preferences.data.delegates

import android.content.SharedPreferences
import androidx.core.content.edit
import com.team.preferences.data.delegates.api.TypedPreference

/** Core delegate for all string prefs */
open class StringPreference(
    preferences: SharedPreferences,
    val key: String,
    val defaultValue: String? = null
) : TypedPreference<String?>(preferences) {

    override fun getPreference(): String? =
        preferences.getString(key, defaultValue)?.takeIf { preferences.contains(key) }

    override fun setPreference(value: String?) {
        preferences.edit { putString(key, value) }
    }
}