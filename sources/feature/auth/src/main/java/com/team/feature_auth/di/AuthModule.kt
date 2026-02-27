package com.team.feature_auth.di

import android.content.Context
import com.team.auth.AuthPreferences
import com.team.feature_auth.data.remote.api.AuthApi
import com.team.feature_auth.data.repository.AuthRepositoryImpl
import com.team.feature_auth.data.repository.FakeAuthRepository
import com.team.feature_auth.domain.repository.AuthRepository
import com.team.network.ApiConstants
import com.team.network.MockConfig
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AuthModule {
    @Provides
    @Singleton
    fun provideOkHttpClient(): OkHttpClient {
        val loggingInterceptor = HttpLoggingInterceptor().apply {
            setLevel(HttpLoggingInterceptor.Level.BODY)
        }
        return OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .connectTimeout(30, java.util.concurrent.TimeUnit.SECONDS)
            .writeTimeout(30, java.util.concurrent.TimeUnit.SECONDS)
            .readTimeout(60, java.util.concurrent.TimeUnit.SECONDS)
            .build()
    }

    @Provides
    @Singleton
    fun provideAuthApi(okHttpClient: OkHttpClient): AuthApi {
        return Retrofit.Builder()
            .baseUrl(ApiConstants.BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(AuthApi::class.java)
    }

    @Provides
    @Singleton
    fun provideAuthRepository(
        api: AuthApi,
        authPreferences: AuthPreferences,
        @ApplicationContext context: Context
    ): AuthRepository {
        return if (MockConfig.USE_MOCK_DATA) {
            FakeAuthRepository(authPreferences)
        } else {
            AuthRepositoryImpl(api, authPreferences, context = context)
        }
    }
}

