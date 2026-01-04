package com.team.feature_auth.data.remote.api.dto

import com.google.gson.annotations.SerializedName

data class AuthResponse(
    @SerializedName("accessToken") val token: String
)
