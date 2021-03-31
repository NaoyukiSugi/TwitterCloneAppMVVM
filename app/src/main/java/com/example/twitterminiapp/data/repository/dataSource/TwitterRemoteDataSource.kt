package com.example.twitterminiapp.data.repository.dataSource

import com.example.twitterminiapp.data.model.Tweet
import com.example.twitterminiapp.data.model.GetSearchedTweetsResponse
import retrofit2.Response

interface TwitterRemoteDataSource {
    suspend fun getTimeline(): Response<List<Tweet>>
    suspend fun getSearchedTimeline(searchQuery: String): Response<GetSearchedTweetsResponse>
}
