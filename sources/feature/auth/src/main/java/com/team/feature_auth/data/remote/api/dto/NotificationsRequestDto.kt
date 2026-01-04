package com.team.feature_auth.data.remote.api.dto

import com.google.gson.annotations.SerializedName

data class NotificationsRequestDto(
    val token: String,
    @SerializedName("device_type") val deviceType: String = "android"
)
