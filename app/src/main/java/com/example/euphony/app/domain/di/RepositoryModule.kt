package com.example.euphony.app.domain.di

import com.example.euphony.app.domain.repository.MusicPlayerRepository
import com.example.euphony.app.data.repository.MusicPlayerRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun provideMusicPlayerRepository(impl: MusicPlayerRepositoryImpl): MusicPlayerRepository
}