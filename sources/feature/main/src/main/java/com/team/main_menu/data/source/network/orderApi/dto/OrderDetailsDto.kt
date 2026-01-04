package com.team.main_menu.data.source.network.orderApi.dto

import com.google.gson.annotations.SerializedName
import com.team.main_menu.data.source.network.deliveryApi.dto.DeliveryBadgeDto
import com.team.main_menu.data.source.network.deliveryApi.dto.DeliveryCompanyDto

data class OrderDetailsDto(
    @SerializedName("offer_id") val offerId: String,
    val tariff: String,
    @SerializedName("company") val company: DeliveryCompanyDto,
    @SerializedName("badge") val badge: DeliveryBadgeDto?,
    @SerializedName("min_price") val minPrice: String,
    @SerializedName("is_estimate") val isEstimate: Boolean,
    @SerializedName("stated_duration") val statedDuration: Int,
    @SerializedName("predicted_duration") val predictedDuration: Int,
    @SerializedName("services") val services: List<OrderServiceDto>
)

data class OrderServiceDto(
    val id: String,
    val name: String,
    @SerializedName("price") val price: String
)
