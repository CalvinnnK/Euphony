package com.example.euphony.app.presentation.di

import com.example.euphony.app.presentation.ui.navigation.MusicPlayerNavigation
import com.example.euphony.app.presentation.ui.navigation.MusicPlayerNavigationImpl
import com.example.euphony.core.utils.AppNavigation
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.android.scopes.ActivityScoped
import javax.inject.Singleton

@Module
@InstallIn(ActivityComponent::class)
abstract class NavigationModule {

    @Binds
    @ActivityScoped
    abstract fun provideMusicPlayerNavigation(impl: MusicPlayerNavigationImpl): MusicPlayerNavigation
}