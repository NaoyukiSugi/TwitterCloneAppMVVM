package com.example.twitterminiapp.domain.datasource

import androidx.paging.DataSource
import com.example.twitterminiapp.data.model.GetSearchedTweet
import com.example.twitterminiapp.domain.repository.TwitterRepository

class GetSearchedTweetsDataSourceFactory(
    private val repository: TwitterRepository,
    private val searchQuery: String
) : DataSource.Factory<String, GetSearchedTweet>() {

    override fun create(): DataSource<String, GetSearchedTweet> =
        GetSearchedTweetsDataSource(repository, searchQuery)
}
