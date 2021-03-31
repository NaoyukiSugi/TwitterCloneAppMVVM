package com.example.twitterminiapp.presentation.di.modules

import com.example.twitterminiapp.data.api.TwitterNewAPIService
import com.example.twitterminiapp.data.api.TwitterOldAPIService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class APIServiceModule {

    @Singleton
    @Provides
    fun provideTwitterOldAPIService(@Named(RetrofitModule.OLD_API_DI_NAME) retrofit: Retrofit): TwitterOldAPIService =
        retrofit.create(TwitterOldAPIService::class.java)

    @Singleton
    @Provides
    fun provideTwitterNewAPIService(@Named(RetrofitModule.NEW_API_DI_NAME) retrofit: Retrofit): TwitterNewAPIService =
        retrofit.create(TwitterNewAPIService::class.java)
}
