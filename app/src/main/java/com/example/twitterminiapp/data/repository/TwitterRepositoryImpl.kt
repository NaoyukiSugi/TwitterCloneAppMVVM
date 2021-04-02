package com.example.twitterminiapp.data.repository

import com.example.twitterminiapp.data.model.Tweet
import com.example.twitterminiapp.data.model.GetSearchedTweetsResponse
import com.example.twitterminiapp.data.model.SearchQuery
import com.example.twitterminiapp.data.repository.dataSource.TwitterRemoteDataSource
import com.example.twitterminiapp.data.util.Result
import com.example.twitterminiapp.domain.repository.TwitterRepository
import retrofit2.Response

class TwitterRepositoryImpl(
    private val remoteDataSource: TwitterRemoteDataSource
) : TwitterRepository {

    override suspend fun getTimeline(): Result<List<Tweet>> {
        return responseToResourceForOldApi(remoteDataSource.getTimeline())
    }

    override suspend fun getSearchedTimeline(
        searchQuery: SearchQuery,
        nextToken: String?
    ): Result<GetSearchedTweetsResponse> {
        return responseToResourceForNewApi(remoteDataSource.getSearchedTimeline(searchQuery, nextToken))
    }

    private fun responseToResourceForOldApi(response: Response<List<Tweet>>): Result<List<Tweet>> {
        if (response.isSuccessful) {
            response.body()?.let { result ->
                return Result.Success(result)
            }
        }
        return Result.Error(response.message())
    }

    private fun responseToResourceForNewApi(responseNewGetSearched: Response<GetSearchedTweetsResponse>): Result<GetSearchedTweetsResponse> {
        if (responseNewGetSearched.isSuccessful) {
            responseNewGetSearched.body()?.let { result ->
                return Result.Success(result)
            }
        }
        return Result.Error(responseNewGetSearched.message())
    }
}
