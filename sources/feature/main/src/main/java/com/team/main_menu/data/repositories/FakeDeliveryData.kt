package com.team.main_menu.data.repositories

import com.team.main_menu.utils.delivery.DeliveryBadge
import com.team.main_menu.utils.delivery.DeliveryCompany
import com.team.main_menu.utils.delivery.DeliveryOffer
import com.team.main_menu.utils.order.AdditionalService
import java.math.BigDecimal

object FakeDeliveryData {

    val offers = listOf(
        DeliveryOffer(
            id = "offer-001",
            tariff = "Экспресс",
            company = DeliveryCompany(name = "СДЭК", logoId = ""),
            badge = DeliveryBadge(text = "Быстрее всего", color = "#4CAF50"),
            minPrice = BigDecimal("1890.00"),
            isEstimate = false,
            statedDuration = 2,
            predictedDuration = 3
        ),
        DeliveryOffer(
            id = "offer-002",
            tariff = "Сборный груз",
            company = DeliveryCompany(name = "СДЭК", logoId = ""),
            badge = null,
            minPrice = BigDecimal("1344.00"),
            isEstimate = false,
            statedDuration = 5,
            predictedDuration = 6
        ),
        DeliveryOffer(
            id = "offer-003",
            tariff = "Экспресс",
            company = DeliveryCompany(name = "Деловые Линии", logoId = ""),
            badge = DeliveryBadge(text = "Лучшая цена", color = "#FF9800"),
            minPrice = BigDecimal("1650.50"),
            isEstimate = true,
            statedDuration = 3,
            predictedDuration = 4
        ),
        DeliveryOffer(
            id = "offer-004",
            tariff = "Сборный груз",
            company = DeliveryCompany(name = "Деловые Линии", logoId = ""),
            badge = null,
            minPrice = BigDecimal("1100.00"),
            isEstimate = false,
            statedDuration = 7,
            predictedDuration = 8
        ),
        DeliveryOffer(
            id = "offer-005",
            tariff = "Сборный груз",
            company = DeliveryCompany(name = "ПЭК", logoId = ""),
            badge = null,
            minPrice = BigDecimal("890.00"),
            isEstimate = true,
            statedDuration = 10,
            predictedDuration = 12
        ),
        DeliveryOffer(
            id = "offer-006",
            tariff = "Сборный груз",
            company = DeliveryCompany(name = "Boxberry", logoId = ""),
            badge = DeliveryBadge(text = "Популярное", color = "#2196F3"),
            minPrice = BigDecimal("1250.00"),
            isEstimate = false,
            statedDuration = 5,
            predictedDuration = 5
        ),
        DeliveryOffer(
            id = "offer-007",
            tariff = "Сборный груз",
            company = DeliveryCompany(name = "Почта России", logoId = ""),
            badge = null,
            minPrice = BigDecimal("650.00"),
            isEstimate = true,
            statedDuration = 14,
            predictedDuration = 18
        ),
    )

    private val offerMap = offers.associateBy { it.id }

    fun getOfferById(id: String): DeliveryOffer? = offerMap[id]

    val services = listOf(
        AdditionalService(
            id = "svc-001",
            name = "Страхование груза",
            price = BigDecimal("350.00")
        ),
        AdditionalService(
            id = "svc-002",
            name = "SMS-уведомления",
            price = BigDecimal("50.00")
        ),
        AdditionalService(
            id = "svc-003",
            name = "Упаковка",
            price = BigDecimal("200.00")
        ),
        AdditionalService(
            id = "svc-004",
            name = "Доставка до двери",
            price = BigDecimal("150.00")
        ),
    )
}
