package com.team.main_menu.data.repositories

import com.team.main_menu.data.mappers.mapToCityModel
import com.team.main_menu.data.mappers.mapToCitySearchHistoryEntity
import com.team.main_menu.data.source.network.citiesApi.CitiesApi
import com.team.main_menu.domain.repositories.CitiesRepository
import com.team.main_menu.utils.cities.CityModel
import com.teils.database.data.room.entities.citysearchhistory.CitySearchHistoryDao
import javax.inject.Inject

class CitiesRepositoryImpl @Inject constructor(
    private val citiesApi: CitiesApi,
    private val citySearchHistoryDao: CitySearchHistoryDao,
) : CitiesRepository {
    override suspend fun searchCities(query: String): Result<List<CityModel>> {
        return runCatching {
            citiesApi.searchCity(query).map {
                it.mapToCityModel()
            }
        }
    }

    override suspend fun saveSearchResult(city: CityModel) {
        citySearchHistoryDao.insert(city.mapToCitySearchHistoryEntity())

        val all = citySearchHistoryDao.getAll()
        if (all.size > 3) citySearchHistoryDao.deleteOldest()
    }

    override suspend fun getSearchHistory(): List<CityModel> {
        return citySearchHistoryDao.getAll().map {
            it.mapToCitySearchHistoryEntity()
        }
    }
}