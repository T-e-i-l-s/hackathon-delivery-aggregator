package com.team.preferences.data.delegates

import android.content.SharedPreferences
import androidx.core.content.edit
import com.team.preferences.data.delegates.api.TypedPreference

/** Core delegate for all int prefs */
open class IntPreference(
    preferences: SharedPreferences,
    val key: String
) : TypedPreference<Int?>(preferences) {

    override fun getPreference(): Int? =
        preferences.getInt(key, 0).takeIf { preferences.contains(key) }

    override fun setPreference(value: Int?) {
        value?.let { preferences.edit { putInt(key, it) } }
    }
}
