package com.teils.database.di

import android.content.Context
import androidx.room.Room
import com.teils.database.Constants.APP_DATABASE_NAME
import com.teils.database.data.room.AppDatabase
import com.teils.database.data.room.entities.citysearchhistory.CitySearchHistoryDao
import com.teils.database.data.room.entities.order.OrderDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object CommonDatabaseModule {
    @Provides
    @Singleton
    fun provideAppDatabase(@ApplicationContext context: Context): AppDatabase {
        return Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            APP_DATABASE_NAME
        ).fallbackToDestructiveMigration().build()
    }

    @Provides
    fun provideCitySearchHistoryDao(db: AppDatabase): CitySearchHistoryDao {
        return db.citySearchHistoryDao()
    }

    @Provides
    fun provideOrderDao(db: AppDatabase): OrderDao {
        return db.orderDao()
    }
}