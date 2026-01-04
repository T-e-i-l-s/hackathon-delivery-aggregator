package com.team.main_menu.domain.repositories

import com.team.main_menu.utils.cities.CityModel

interface CitiesRepository {
    suspend fun searchCities(query: String): Result<List<CityModel>>
    suspend fun saveSearchResult(city: CityModel)
    suspend fun getSearchHistory(): List<CityModel>
}