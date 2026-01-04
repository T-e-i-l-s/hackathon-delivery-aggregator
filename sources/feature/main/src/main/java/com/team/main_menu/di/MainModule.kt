package com.team.main_menu.di

import android.content.SharedPreferences
import com.team.auth.AuthPreferences
import com.team.main_menu.data.repositories.CitiesRepositoryImpl
import com.team.main_menu.data.repositories.DeliveryRepositoryImpl
import com.team.main_menu.data.repositories.OrderRepositoryImpl
import com.team.main_menu.data.repositories.WeightLimitRepositoryImpl
import com.team.main_menu.data.source.local.prefs.WeightLimitPrefs
import com.team.main_menu.data.source.network.citiesApi.CitiesApi
import com.team.main_menu.data.source.network.deliveryApi.DeliveryApi
import com.team.main_menu.data.source.network.orderApi.OrderApi
import com.team.main_menu.domain.repositories.CitiesRepository
import com.team.main_menu.domain.repositories.DeliveryRepository
import com.team.main_menu.domain.repositories.OrderRepository
import com.team.main_menu.domain.repositories.WeightLimitRepository
import com.team.network.ApiConstants
import com.teils.database.data.room.entities.citysearchhistory.CitySearchHistoryDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object MainModule {
    @Provides
    @Singleton
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl(ApiConstants.BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Provides
    @Singleton
    fun provideCitiesApi(retrofit: Retrofit): CitiesApi {
        return retrofit.create(CitiesApi::class.java)
    }

    @Provides
    @Singleton
    fun provideDeliveryApi(retrofit: Retrofit): DeliveryApi {
        return retrofit.create(DeliveryApi::class.java)
    }

    @Provides
    @Singleton
    fun provideOrderApi(retrofit: Retrofit): OrderApi {
        return retrofit.create(OrderApi::class.java)
    }

    @Provides
    @Singleton
    fun provideWeightLimitPrefs(sharedPreferences: SharedPreferences): WeightLimitPrefs {
        return WeightLimitPrefs(sharedPreferences)
    }

    @Provides
    @Singleton
    fun provideCitiesRepository(
        citiesApi: CitiesApi,
        citySearchHistoryDao: CitySearchHistoryDao
    ): CitiesRepository {
        return CitiesRepositoryImpl(citiesApi, citySearchHistoryDao)
    }

    @Provides
    @Singleton
    fun provideDeliveryRepository(
        deliveryApi: DeliveryApi,
        authPreferences: AuthPreferences
    ): DeliveryRepository {
        return DeliveryRepositoryImpl(authPreferences, deliveryApi)
    }

    @Provides
    @Singleton
    fun provideOrderRepository(
        orderApi: OrderApi,
        authPreferences: AuthPreferences
    ): OrderRepository {
        return OrderRepositoryImpl(authPreferences, orderApi)
    }

    @Provides
    @Singleton
    fun provideWeightLimitRepository(weightLimitPrefs: WeightLimitPrefs): WeightLimitRepository {
        return WeightLimitRepositoryImpl(weightLimitPrefs)
    }
}
