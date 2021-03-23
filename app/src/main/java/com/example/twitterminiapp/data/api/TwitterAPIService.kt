package com.example.twitterminiapp.data.api

import com.example.twitterminiapp.BuildConfig
import com.example.twitterminiapp.data.model.Tweet
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Headers

interface TwitterAPIService {
    @Headers(BuildConfig.OAUTH_HEADER_STRING)
    @GET("1.1/statuses/home_timeline.json")
    suspend fun getTimeline(): Response<List<Tweet>>
}
