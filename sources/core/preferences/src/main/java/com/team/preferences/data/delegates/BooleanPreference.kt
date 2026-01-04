package com.team.preferences.data.delegates

import android.content.SharedPreferences
import androidx.core.content.edit
import com.team.preferences.data.delegates.api.TypedPreference

/** Core delegate for all boolean prefs */
open class BooleanPreference(
    preferences: SharedPreferences,
    val key: String,
    val defaultValue: Boolean = false
) : TypedPreference<Boolean?>(preferences) {

    override fun getPreference(): Boolean? =
        preferences.getBoolean(key, defaultValue).takeIf { preferences.contains(key) }

    override fun setPreference(value: Boolean?) {
        value?.let { preferences.edit { putBoolean(key, it) } }
    }
}