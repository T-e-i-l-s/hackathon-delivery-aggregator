package com.team.main_menu.utils.cities

import java.math.BigDecimal

data class CityModel(
    val id: Int,
    val name: String,
    val region: String,
    val minPrice: BigDecimal
)
