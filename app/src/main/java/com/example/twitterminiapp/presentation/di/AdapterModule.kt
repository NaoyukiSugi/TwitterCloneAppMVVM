package com.example.twitterminiapp.presentation.di

import com.example.twitterminiapp.presentation.adapter.TwitterAdapter
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
class AdapterModule {

    @Singleton
    @Provides
    fun provideTwitterAdapter(): TwitterAdapter {
        return TwitterAdapter()
    }
}
