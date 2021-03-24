package com.example.twitterminiapp.presentation.di

import com.example.twitterminiapp.data.api.TwitterAPIService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
class APIServiceModule {

    @Singleton
    @Provides
    fun provideTwitterAPIService(retrofit: Retrofit): TwitterAPIService =
        retrofit.create(TwitterAPIService::class.java)
}
