package com.team.main_menu.data.source.network.citiesApi.dto

import com.google.gson.annotations.SerializedName
import kotlinx.serialization.Serializable

@Serializable
data class CityDto(
    val id: Int,
    @SerializedName("city_name") val cityName: String,
    @SerializedName("region_name") val regionName: String,
    @SerializedName("min_price") val minPrice: String
)