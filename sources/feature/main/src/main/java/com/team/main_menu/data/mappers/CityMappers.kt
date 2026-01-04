package com.team.main_menu.data.mappers

import com.team.main_menu.data.source.network.citiesApi.dto.CityDto
import com.team.main_menu.utils.cities.CityModel
import com.teils.database.data.room.entities.citysearchhistory.CitySearchHistoryEntity
import java.math.BigDecimal

fun CityDto.mapToCityModel() = CityModel(
    id = id,
    name = cityName,
    region = regionName,
    minPrice = minPrice.toBigDecimalOrNull() ?: BigDecimal.TEN
)

fun CityModel.mapToCitySearchHistoryEntity() = CitySearchHistoryEntity(
    id = id,
    name = name,
    region = region,
    minPrice = minPrice.toString()
)

fun CitySearchHistoryEntity.mapToCitySearchHistoryEntity() = CityModel(
    id = id,
    name = name,
    region = region,
    minPrice = BigDecimal(minPrice)
)