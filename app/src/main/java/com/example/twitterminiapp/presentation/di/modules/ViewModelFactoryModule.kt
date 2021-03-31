package com.example.twitterminiapp.presentation.di.modules

import android.app.Application
import com.example.twitterminiapp.domain.usecase.GetSearchedTimelineUseCase
import com.example.twitterminiapp.domain.usecase.GetTimelineUseCase
import com.example.twitterminiapp.presentation.viewmodel.TwitterViewModelFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class ViewModelFactoryModule {

    @Singleton
    @Provides
    fun provideTwitterViewModelFactory(
        app: Application,
        getTimelineUseCase: GetTimelineUseCase,
        getSearchedTimelineUseCase: GetSearchedTimelineUseCase
    ): TwitterViewModelFactory =
        TwitterViewModelFactory(app, getTimelineUseCase, getSearchedTimelineUseCase)
}
