package com.example.twitterminiapp.presentation.di

import com.example.twitterminiapp.presentation.adapter.TwitterAdapter
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AdapterModule {

    @Singleton
    @Provides
    fun provideTwitterAdapter(): TwitterAdapter {
        return TwitterAdapter()
    }
}
