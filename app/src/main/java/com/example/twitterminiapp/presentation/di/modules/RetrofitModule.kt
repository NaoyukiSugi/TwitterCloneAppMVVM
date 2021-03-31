package com.example.twitterminiapp.presentation.di.modules

import com.example.twitterminiapp.BuildConfig
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class RetrofitModule {

    @Singleton
    @Provides
    @Named(OLD_API_DI_NAME)
    fun provideRetrofitForOldApi(): Retrofit =
        Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(BASE_URL)
            .client(
                OkHttpClient.Builder()
                    .addInterceptor(Interceptor { chain ->
                        val newRequest = chain.request().newBuilder()
                            .addHeader(
                                "Authorization",
                                BuildConfig.OAUTH_HEADER_STRING
                            )
                            .build()
                        return@Interceptor chain.proceed(newRequest)
                    })
                    .addInterceptor(
                        HttpLoggingInterceptor().apply {
                            level = HttpLoggingInterceptor.Level.BODY
                        })
                    .build()
            )
            .build()

    @Singleton
    @Provides
    @Named(NEW_API_DI_NAME)
    fun provideRetrofitForNewApi(): Retrofit =
        Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(BASE_URL)
            .client(
                OkHttpClient.Builder()
                    .addInterceptor(Interceptor { chain ->
                        val newRequest = chain.request().newBuilder()
                            .addHeader(
                                "Authorization",
                                BuildConfig.BEARER_TOKEN
                            )
                            .build()
                        return@Interceptor chain.proceed(newRequest)
                    })
                    .addInterceptor(
                        HttpLoggingInterceptor().apply {
                            level = HttpLoggingInterceptor.Level.BODY
                        }
                    )
                    .build()
            )
            .build()

    companion object {
        private const val BASE_URL = "https://api.twitter.com/"
        const val OLD_API_DI_NAME = "api_v1_1"
        const val NEW_API_DI_NAME = "api_v2"
    }
}
