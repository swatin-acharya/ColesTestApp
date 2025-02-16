package com.example.colestestapp.di

import android.content.Context
import com.example.colestestapp.api.MainApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class ApiModule {

    @Provides
    @Singleton
    fun provideMainApiService(@ApplicationContext application: Context): MainApiService {
        return MainApiService(application.assets)
    }
}