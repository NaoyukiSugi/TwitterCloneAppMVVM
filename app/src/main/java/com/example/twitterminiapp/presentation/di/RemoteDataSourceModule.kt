package com.example.twitterminiapp.presentation.di

import com.example.twitterminiapp.data.api.TwitterAPIService
import com.example.twitterminiapp.data.repository.dataSource.TwitterRemoteDataSource
import com.example.twitterminiapp.data.repository.dataSourceImpl.TwitterRemoteDataSourceImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
class RemoteDataSourceModule {

    @Singleton
    @Provides
    fun provideTwitterRemoteDataSource(apiService: TwitterAPIService): TwitterRemoteDataSource =
        TwitterRemoteDataSourceImpl(apiService)
}
