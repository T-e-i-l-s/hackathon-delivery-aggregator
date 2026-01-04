package com.team.main_menu.data.repositories

import com.team.main_menu.data.source.local.prefs.WeightLimitPrefs
import com.team.main_menu.domain.repositories.WeightLimitRepository
import com.team.main_menu.domain.weights.WeightLimits
import javax.inject.Inject

class WeightLimitRepositoryImpl @Inject constructor(
    private val weightLimitPrefs: WeightLimitPrefs
) : WeightLimitRepository {
    override fun updateWeightLimit(newLimit: Int) {
        weightLimitPrefs.lastSelectedWeightLimit = newLimit
    }

    override fun getWeightLimit(): Int =
        weightLimitPrefs.lastSelectedWeightLimit ?: WeightLimits.KG1.limitKg
}