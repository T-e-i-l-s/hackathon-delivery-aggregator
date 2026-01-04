package com.team.main_menu.data.source.local.prefs

import android.content.SharedPreferences
import com.team.preferences.data.delegates.IntPreference
import javax.inject.Inject

class WeightLimitPrefs @Inject constructor(
    sharedPreferences: SharedPreferences
) {
    companion object {
        const val WEIGHT_LIMIT_KEY = "weight_limit"
    }

    var lastSelectedWeightLimit by IntPreference(sharedPreferences, WEIGHT_LIMIT_KEY)
}