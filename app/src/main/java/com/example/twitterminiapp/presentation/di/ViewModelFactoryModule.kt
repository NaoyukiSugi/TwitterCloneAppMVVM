package com.example.twitterminiapp.presentation.di

import android.app.Application
import com.example.twitterminiapp.domain.usecase.GetTimelineUseCase
import com.example.twitterminiapp.presentation.viewmodel.TwitterViewModelFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
class ViewModelFactoryModule {

    @Singleton
    @Provides
    fun provideTwitterViewModelFactory(
        app: Application,
        getTimelineUseCase: GetTimelineUseCase
    ): TwitterViewModelFactory = TwitterViewModelFactory(app, getTimelineUseCase)
}
