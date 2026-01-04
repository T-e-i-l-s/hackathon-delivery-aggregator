package com.team.main_menu.data.source.network.deliveryApi

import com.team.main_menu.data.source.network.deliveryApi.dto.DeliveryCalculateRequestDto
import com.team.main_menu.data.source.network.deliveryApi.dto.DeliveryOfferDto
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Path

interface DeliveryApi {
    @POST("delivery/calculate")
    suspend fun getOffers(
        @Header("Authorization") token: String,
        @Body request: DeliveryCalculateRequestDto
    ): List<DeliveryOfferDto>

    @GET("delivery/offers/{id}")
    suspend fun getOfferById(
        @Header("Authorization") token: String,
        @Path("id") id: String
    ): DeliveryOfferDto
}
