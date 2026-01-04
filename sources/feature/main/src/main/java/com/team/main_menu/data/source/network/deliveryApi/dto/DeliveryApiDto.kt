package com.team.main_menu.data.source.network.deliveryApi.dto

import com.google.gson.annotations.SerializedName

data class DeliveryCalculateRequestDto(
    @SerializedName("city_id") val cityId: Int,
    val weight: Int
)

data class DeliveryOfferDto(
    @SerializedName("offer_id") val offerId: String,
    val tariff: String,
    val company: DeliveryCompanyDto,
    val badge: DeliveryBadgeDto?,
    @SerializedName("min_price") val minPrice: String,
    @SerializedName("is_estimate") val isEstimate: Boolean,
    @SerializedName("stated_duration") val statedDuration: Int,
    @SerializedName("predicted_duration") val predictedDuration: Int,
)

data class DeliveryCompanyDto(
    val name: String,
    @SerializedName("logo_id") val logoId: String,
)

data class DeliveryBadgeDto(
    val text: String?,
    val color: String,
)
