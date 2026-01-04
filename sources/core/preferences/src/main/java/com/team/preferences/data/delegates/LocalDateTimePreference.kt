package com.team.preferences.data.delegates

import android.content.SharedPreferences
import androidx.core.content.edit
import com.team.preferences.data.delegates.api.TypedPreference
import com.team.utils.localdatetime.epochSecondsToLocalDateTime
import com.team.utils.localdatetime.localDateTimeToEpochSeconds
import java.time.LocalDateTime

/** Core delegate for all LocalDateTime prefs */
open class LocalDateTimePreference(
    preferences: SharedPreferences,
    private val key: String,
    private val defaultValue: LocalDateTime = LocalDateTime.now()
) : TypedPreference<LocalDateTime?>(preferences) {

    override fun getPreference(): LocalDateTime? {
        val defaultTimeEpochSeconds = defaultValue.localDateTimeToEpochSeconds()
        val valueEpochSeconds = preferences.getLong(key, defaultTimeEpochSeconds)
        return valueEpochSeconds.epochSecondsToLocalDateTime().takeIf { preferences.contains(key) }
    }

    override fun setPreference(value: LocalDateTime?) {
        preferences.edit {
            value?.let { putLong(key, it.localDateTimeToEpochSeconds()) } ?: remove(key)
        }
    }
}
