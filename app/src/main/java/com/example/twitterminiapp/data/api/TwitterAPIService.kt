package com.example.twitterminiapp.data.api

import com.example.twitterminiapp.data.model.Tweet
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface TwitterAPIService {
    @GET("1.1/statuses/home_timeline.json")
    suspend fun getTimeline(): Response<List<Tweet>>

    @GET("1.1/search/tweets.json")
    suspend fun getSearchedTimeline(@Query("q") searchQuery: String): Response<List<Tweet>>
}
