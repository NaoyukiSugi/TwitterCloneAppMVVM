package com.example.twitterminiapp.presentation.di.modules

import com.example.twitterminiapp.domain.repository.TwitterRepository
import com.example.twitterminiapp.domain.usecase.GetTimelineUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class UseCaseModule {

    @Singleton
    @Provides
    fun provideGetTimelineUseCase(twitterRepository: TwitterRepository): GetTimelineUseCase =
        GetTimelineUseCase(twitterRepository)
}
