package com.example.twitterminiapp.presentation.di

import com.example.twitterminiapp.data.repository.TwitterRepositoryImpl
import com.example.twitterminiapp.data.repository.dataSource.TwitterRemoteDataSource
import com.example.twitterminiapp.domain.repository.TwitterRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
class RepositoryModule {

    @Singleton
    @Provides
    fun provideTwitterRepository(remoteDataSource: TwitterRemoteDataSource): TwitterRepository =
        TwitterRepositoryImpl(remoteDataSource)
}
