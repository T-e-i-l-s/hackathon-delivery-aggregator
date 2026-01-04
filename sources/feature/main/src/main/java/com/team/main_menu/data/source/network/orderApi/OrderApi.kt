package com.team.main_menu.data.source.network.orderApi

import com.team.main_menu.data.source.network.orderApi.dto.OrderDetailsDto
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Path

interface OrderApi {
    @GET("delivery/offers/{id}/details")
    suspend fun getOrderDetails(
        @Header("Authorization") token: String,
        @Path("id") id: String
    ): OrderDetailsDto
}
