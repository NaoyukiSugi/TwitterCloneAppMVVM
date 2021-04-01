package com.example.twitterminiapp.data.repository.dataSourceImpl

import com.example.twitterminiapp.data.api.TwitterNewAPIService
import com.example.twitterminiapp.data.api.TwitterOldAPIService
import com.example.twitterminiapp.data.model.Tweet
import com.example.twitterminiapp.data.model.GetSearchedTweetsResponse
import com.example.twitterminiapp.data.repository.dataSource.TwitterRemoteDataSource
import retrofit2.Response

class TwitterRemoteDataSourceImpl(
    private val oldApiService: TwitterOldAPIService,
    private val newApiService: TwitterNewAPIService
) : TwitterRemoteDataSource {

    override suspend fun getTimeline(): Response<List<Tweet>> {
        return oldApiService.getTimeline()
    }

    override suspend fun getSearchedTimeline(
        searchQuery: String,
        nextToken: String?
    ): Response<GetSearchedTweetsResponse> {
        return newApiService.getSearchedTimeline(searchQuery, nextToken = nextToken)
    }
}
