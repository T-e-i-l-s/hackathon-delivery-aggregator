package com.team.preferences.data.delegates

import android.content.SharedPreferences
import androidx.core.content.edit
import com.team.preferences.data.delegates.api.TypedPreference

/** Core delegate for all long prefs */
open class LongPreference(
    preferences: SharedPreferences,
    val key: String,
    val defaultValue: Long = 0L
) : TypedPreference<Long?>(preferences) {

    override fun getPreference(): Long? =
        preferences.getLong(key, defaultValue).takeIf { preferences.contains(key) }

    override fun setPreference(value: Long?) {
        value?.let { preferences.edit { putLong(key, it) } }
    }
}