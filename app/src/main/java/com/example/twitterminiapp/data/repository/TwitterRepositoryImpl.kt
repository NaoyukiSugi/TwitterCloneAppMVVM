package com.example.twitterminiapp.data.repository

import com.example.twitterminiapp.data.model.Tweet
import com.example.twitterminiapp.data.repository.dataSource.TwitterRemoteDataSource
import com.example.twitterminiapp.data.util.Resource
import com.example.twitterminiapp.domain.repository.TwitterRepository
import retrofit2.Response

class TwitterRepositoryImpl(
    private val remoteDataSource: TwitterRemoteDataSource
) : TwitterRepository {

    override suspend fun getTimeline(): Resource<List<Tweet>> {
        return responseToResource(remoteDataSource.getTimeline())
    }

    private fun responseToResource(response: Response<List<Tweet>>): Resource<List<Tweet>> {
        if (response.isSuccessful) {
            response.body()?.let { result ->
                return Resource.Success(result)
            }
        }
        return Resource.Error(response.message())
    }
}
