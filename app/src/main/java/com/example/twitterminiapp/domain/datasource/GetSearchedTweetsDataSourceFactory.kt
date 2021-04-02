package com.example.twitterminiapp.domain.datasource

import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import com.example.twitterminiapp.data.model.SearchQuery
import com.example.twitterminiapp.data.model.Tweet
import com.example.twitterminiapp.domain.repository.TwitterRepository

class GetSearchedTweetsDataSourceFactory(
    private val searchedDataLoadingResultLiveData: MutableLiveData<SearchedTweetsDataLoadingResult> = MutableLiveData(),
    private val repository: TwitterRepository,
    private val searchQuery: SearchQuery
) : DataSource.Factory<String, Tweet>() {

    override fun create(): DataSource<String, Tweet> =
        GetSearchedTweetsDataSource(
            searchedDataLoadingResultLiveData = searchedDataLoadingResultLiveData,
            repository = repository,
            searchQuery = searchQuery
        )

    fun getSearchedDataLoadingResultLiveData() = searchedDataLoadingResultLiveData
}
