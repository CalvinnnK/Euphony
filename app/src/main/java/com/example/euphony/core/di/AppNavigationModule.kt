package com.example.euphony.core.di

import com.example.euphony.core.utils.AppNavigation
import com.example.euphony.core.utils.AppNavigationImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
abstract class AppNavigationModule {

    @Binds
    @Singleton
    abstract fun bindAppNavigation(
        impl: AppNavigationImpl
    ): AppNavigation
}