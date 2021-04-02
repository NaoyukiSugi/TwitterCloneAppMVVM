package com.example.twitterminiapp.presentation.di.modules

import com.example.twitterminiapp.presentation.adapter.SearchResultAdapter
import com.example.twitterminiapp.presentation.adapter.HomeAdapter
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
    fun provideHomeAdapter(): HomeAdapter {
        return HomeAdapter()
    }

    @Singleton
    @Provides
    fun provideSearchResultAdapter(): SearchResultAdapter {
        return SearchResultAdapter()
    }
}
