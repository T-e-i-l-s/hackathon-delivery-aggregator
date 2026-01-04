package com.team.feature_auth.data.remote.api

import com.team.feature_auth.data.remote.api.dto.AuthResponse
import com.team.feature_auth.data.remote.api.dto.FileUploadResponse
import com.team.feature_auth.data.remote.api.dto.LoginRequestDto
import com.team.feature_auth.data.remote.api.dto.NotificationsRequestDto
import com.team.feature_auth.data.remote.api.dto.RegisterRequestDto
import com.team.feature_auth.data.remote.api.dto.YandexToken
import okhttp3.MultipartBody
import retrofit2.http.Body
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part

interface AuthApi {
    @POST("auth/register")
    suspend fun register(@Body request: RegisterRequestDto)

    @Multipart
    @POST("storage/upload")
    suspend fun uploadFile(
        @Part file: MultipartBody.Part
    ): FileUploadResponse

    @POST("auth/sign-in")
    suspend fun login(@Body request: LoginRequestDto): AuthResponse

    @POST("auth/oauth/yandex")
    suspend fun signInWithYandex(@Body token: YandexToken): AuthResponse

    @POST("notifications/device-tokens")
    suspend fun sendDeviceToken(@Body info: NotificationsRequestDto)
}