package com.example.twitterminiapp.presentation.di.modules

import com.example.twitterminiapp.data.api.TwitterNewAPIService
import com.example.twitterminiapp.data.api.TwitterOldAPIService
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
    fun provideTwitterRemoteDataSource(
        oldApiService: TwitterOldAPIService,
        newApiService: TwitterNewAPIService
    ): TwitterRemoteDataSource =
        TwitterRemoteDataSourceImpl(oldApiService, newApiService)
}
