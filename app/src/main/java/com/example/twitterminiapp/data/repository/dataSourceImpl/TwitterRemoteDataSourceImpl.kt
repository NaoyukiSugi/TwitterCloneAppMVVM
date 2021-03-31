package com.example.twitterminiapp.data.repository.dataSourceImpl

import com.example.twitterminiapp.data.api.TwitterAPIService
import com.example.twitterminiapp.data.model.Tweet
import com.example.twitterminiapp.data.repository.dataSource.TwitterRemoteDataSource
import retrofit2.Response

class TwitterRemoteDataSourceImpl(
    private val apiService: TwitterAPIService
) : TwitterRemoteDataSource {

    override suspend fun getTimeline(): Response<List<Tweet>> {
        return apiService.getTimeline()
    }

    override suspend fun getSearchedTimeline(searchQuery: String): Response<List<Tweet>> {
        return apiService.getSearchedTimeline(searchQuery)
    }
}
