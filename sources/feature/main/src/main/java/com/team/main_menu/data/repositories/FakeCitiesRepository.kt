package com.team.main_menu.data.repositories

import com.team.main_menu.data.mappers.mapToCitySearchHistoryEntity
import com.team.main_menu.domain.cities.Cities
import com.team.main_menu.domain.repositories.CitiesRepository
import com.team.main_menu.utils.cities.CityModel
import com.teils.database.data.room.entities.citysearchhistory.CitySearchHistoryDao
import kotlinx.coroutines.delay
import java.math.BigDecimal

class FakeCitiesRepository(
    private val citySearchHistoryDao: CitySearchHistoryDao
) : CitiesRepository {

    private val allCities: List<CityModel> = Cities.entries.map { it.info } + listOf(
        CityModel(id = 100, name = "Екатеринбург", region = "Свердловская область", minPrice = BigDecimal("1580.50")),
        CityModel(id = 200, name = "Ростов-на-Дону", region = "Ростовская область", minPrice = BigDecimal("1450.00")),
        CityModel(id = 300, name = "Самара", region = "Самарская область", minPrice = BigDecimal("1520.75")),
        CityModel(id = 400, name = "Красноярск", region = "Красноярский край", minPrice = BigDecimal("1690.30")),
        CityModel(id = 500, name = "Воронеж", region = "Воронежская область", minPrice = BigDecimal("1380.00")),
    )

    override suspend fun searchCities(query: String): Result<List<CityModel>> {
        delay(300)
        val filtered = allCities.filter {
            it.name.contains(query, ignoreCase = true) ||
                it.region.contains(query, ignoreCase = true)
        }
        return Result.success(filtered)
    }

    override suspend fun saveSearchResult(city: CityModel) {
        citySearchHistoryDao.insert(city.mapToCitySearchHistoryEntity())
        val all = citySearchHistoryDao.getAll()
        if (all.size > 3) citySearchHistoryDao.deleteOldest()
    }

    override suspend fun getSearchHistory(): List<CityModel> {
        return citySearchHistoryDao.getAll().map { it.mapToCitySearchHistoryEntity() }
    }
}
