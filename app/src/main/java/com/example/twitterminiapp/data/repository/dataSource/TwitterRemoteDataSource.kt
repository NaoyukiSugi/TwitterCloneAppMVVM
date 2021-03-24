package com.example.twitterminiapp.data.repository.dataSource

import com.example.twitterminiapp.data.model.Tweet
import retrofit2.Response

interface TwitterRemoteDataSource {
    suspend fun getTimeline(): Response<List<Tweet>>
}
