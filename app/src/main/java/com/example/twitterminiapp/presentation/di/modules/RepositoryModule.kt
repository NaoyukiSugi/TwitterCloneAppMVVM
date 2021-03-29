package com.example.twitterminiapp.presentation.di.modules

import com.example.twitterminiapp.data.repository.TwitterRepositoryImpl
import com.example.twitterminiapp.data.repository.dataSource.TwitterRemoteDataSource
import com.example.twitterminiapp.domain.repository.TwitterRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class RepositoryModule {

    @Singleton
    @Provides
    fun provideTwitterRepository(remoteDataSource: TwitterRemoteDataSource): TwitterRepository =
        TwitterRepositoryImpl(remoteDataSource)
}
