package com.example.euphony.app.data.di

import com.example.euphony.app.data.remote.ApiMusicService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class ApiModule {

    @Provides
    @Singleton
    fun provideApiMusicService(retrofit: Retrofit): ApiMusicService {
        return retrofit.create(ApiMusicService::class.java)
    }

}