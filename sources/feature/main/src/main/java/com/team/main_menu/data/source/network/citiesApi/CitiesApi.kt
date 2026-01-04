package com.team.main_menu.data.source.network.citiesApi

import com.team.main_menu.data.source.network.citiesApi.dto.CityDto
import retrofit2.http.GET
import retrofit2.http.Query

interface CitiesApi {
    @GET("cities/search")
    suspend fun searchCity(@Query("q") query: String): List<CityDto>
}