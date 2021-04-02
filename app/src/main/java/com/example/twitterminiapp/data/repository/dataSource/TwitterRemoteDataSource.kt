package com.example.twitterminiapp.data.repository.dataSource

import com.example.twitterminiapp.data.model.Tweet
import com.example.twitterminiapp.data.model.GetSearchedTweetsResponse
import com.example.twitterminiapp.data.model.SearchQuery
import retrofit2.Response

interface TwitterRemoteDataSource {
    suspend fun getTimeline(): Response<List<Tweet>>
    suspend fun getSearchedTimeline(searchQuery: SearchQuery, nextToken: String?): Response<GetSearchedTweetsResponse>
}
