package com.example.twitterminiapp.presentation.di.modules

import com.example.twitterminiapp.data.api.TwitterAPIService
import com.example.twitterminiapp.data.repository.dataSource.TwitterRemoteDataSource
import com.example.twitterminiapp.data.repository.dataSourceImpl.TwitterRemoteDataSourceImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class RemoteDataSourceModule {

    @Singleton
    @Provides
    fun provideTwitterRemoteDataSource(apiService: TwitterAPIService): TwitterRemoteDataSource =
        TwitterRemoteDataSourceImpl(apiService)
}
