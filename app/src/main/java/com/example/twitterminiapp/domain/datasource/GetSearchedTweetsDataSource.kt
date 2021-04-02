package com.example.twitterminiapp.domain.datasource

import androidx.lifecycle.MutableLiveData
import androidx.paging.PageKeyedDataSource
import com.example.twitterminiapp.data.model.GetSearchedTweetsResponse
import com.example.twitterminiapp.data.model.Tweet
import com.example.twitterminiapp.domain.repository.TwitterRepository
import kotlinx.coroutines.*

class GetSearchedTweetsDataSource(
    private val searchedDataLoadingResultLiveData: MutableLiveData<SearchedTweetsDataLoadingResult>,
    private val repository: TwitterRepository,
    private val searchQuery: String
) : PageKeyedDataSource<String, Tweet>(), CoroutineScope by MainScope() {
    override fun loadInitial(
        params: LoadInitialParams<String>,
        callback: LoadInitialCallback<String, Tweet>
    ) {

        launch {
            val response = fetch(null) ?: return@launch
            if (response.tweets == null) {
                searchedDataLoadingResultLiveData.postValue(SearchedTweetsDataLoadingResult.NotFound)
                return@launch
            }
            val tweets = convertToTweets(response)
            val nextToken = response.meta.nextToken
            searchedDataLoadingResultLiveData.postValue(SearchedTweetsDataLoadingResult.Found)
            callback.onResult(tweets, null, nextToken)
        }
    }

    override fun loadBefore(
        params: LoadParams<String>,
        callback: LoadCallback<String, Tweet>
    ) {
        // do nothing
    }

    override fun loadAfter(
        params: LoadParams<String>,
        callback: LoadCallback<String, Tweet>
    ) {
        launch {
            if (params.key == null) return@launch
            val response = fetch(null) ?: return@launch
            if (response.tweets == null) {
                searchedDataLoadingResultLiveData.postValue(SearchedTweetsDataLoadingResult.NotFound)
                return@launch
            }
            val tweets = convertToTweets(response)
            val nextToken = response.meta.nextToken
            searchedDataLoadingResultLiveData.postValue(SearchedTweetsDataLoadingResult.Found)
            callback.onResult(tweets, nextToken)
        }
    }

    private suspend fun fetch(nextToken: String?): GetSearchedTweetsResponse? =
        try {
            repository.getSearchedTimeline(searchQuery = searchQuery, nextToken = nextToken).data
        } catch (error: Exception) {
            withContext(Dispatchers.Main) {
                searchedDataLoadingResultLiveData
                    .postValue(SearchedTweetsDataLoadingResult.Failed(error))
            }
            null
        }

    private fun convertToTweets(searchedTweetsResponse: GetSearchedTweetsResponse): List<Tweet> {
        val tweets = searchedTweetsResponse.let { response ->
            val userIdMap = response.includes.users.associateBy { it.id }
            response.tweets.map {
                Tweet(
                    id = it.id,
                    createdAt = it.createdAt,
                    text = it.text,
                    user = userIdMap[it.authorId]!!
                )
            }
        }
        return tweets
    }
}
