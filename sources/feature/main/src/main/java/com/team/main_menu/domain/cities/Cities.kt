package com.team.main_menu.domain.cities

import com.team.main_menu.utils.cities.CityModel
import java.math.BigDecimal

enum class Cities(val info: CityModel) {

    SAINT_PETERSBURG(
        CityModel(
            id = 1896,
            name = "Санкт-Петербург",
            region = "Санкт-Петербург",
            minPrice = BigDecimal("1344.0")
        )
    ),

    KAZAN(
        CityModel(
            id = 811,
            name = "Казань",
            region = "Казань",
            minPrice = BigDecimal("1705.84")
        )
    ),

    NIZHNY_NOVGOROD(
        CityModel(
            id = 1431,
            name = "Нижний Новгород",
            region = "Нижний Новгород",
            minPrice = BigDecimal("1344.0")
        )
    ),

    NOVOSIBIRSK(
        CityModel(
            id = 1522,
            name = "Новосибирск",
            region = "Новосибирск",
            minPrice = BigDecimal("1724.58")
        )
    ),

    VLADIVOSTOK(
        CityModel(
            id = 387,
            name = "Владивосток",
            region = "Приморский край",
            minPrice = BigDecimal("1810.04")
        )
    );
}
