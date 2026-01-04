package com.team.uikit.di

import android.content.Context
import com.team.uikit.presentation.vibration.ProdUiVibrator
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class UiKitModule {
    @Provides
    @Singleton
    fun provideProdUiVibrator(@ApplicationContext context: Context): ProdUiVibrator {
        return ProdUiVibrator(context)
    }
}