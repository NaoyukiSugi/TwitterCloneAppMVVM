package com.example.twitterminiapp.domain.datasource

import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import com.example.twitterminiapp.data.model.Tweet
import com.example.twitterminiapp.domain.repository.TwitterRepository
import com.example.twitterminiapp.domain.usecase.GetSearchedTimelineUseCase

class GetSearchedTweetsDataSourceFactory(
    private val getSearchedTimelineUseCase: GetSearchedTimelineUseCase,
    private val searchedDataLoadingResultLiveData: MutableLiveData<SearchedTweetsDataLoadingResult> = MutableLiveData(),
    private val repository: TwitterRepository,
    private val searchQuery: String
) : DataSource.Factory<String, Tweet>() {

    override fun create(): DataSource<String, Tweet> =
        GetSearchedTweetsDataSource(
            getSearchedTimelineUseCase = getSearchedTimelineUseCase,
            searchedDataLoadingResultLiveData = searchedDataLoadingResultLiveData,
            repository = repository,
            searchQuery = searchQuery
        )

    fun getSearchedDataLoadingResultLiveData() = searchedDataLoadingResultLiveData
}
