package com.team.main_menu.domain.repositories

interface WeightLimitRepository {
    fun updateWeightLimit(newLimit: Int)
    fun getWeightLimit(): Int
}