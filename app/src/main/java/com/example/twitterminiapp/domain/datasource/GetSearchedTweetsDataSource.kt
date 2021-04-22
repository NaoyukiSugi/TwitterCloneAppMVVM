package com.example.twitterminiapp.domain.datasource

import androidx.annotation.VisibleForTesting
import androidx.lifecycle.MutableLiveData
import androidx.paging.PageKeyedDataSource
import com.example.twitterminiapp.data.model.GetSearchedTweetsResponse
import com.example.twitterminiapp.data.model.SearchQuery
import com.example.twitterminiapp.data.model.Tweet
import com.example.twitterminiapp.domain.repository.TwitterRepository
import kotlinx.coroutines.*

class GetSearchedTweetsDataSource(
    private val searchedDataLoadingResultLiveData: MutableLiveData<SearchedTweetsDataLoadingResult>,
    private val repository: TwitterRepository,
    private val searchQuery: SearchQuery
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

    @VisibleForTesting
    internal suspend fun fetch(nextToken: String?): GetSearchedTweetsResponse? =
        try {
            repository.getSearchedTimeline(searchQuery = searchQuery, nextToken = nextToken).data
        } catch (error: Exception) {
            withContext(Dispatchers.Main) {
                searchedDataLoadingResultLiveData
                    .postValue(SearchedTweetsDataLoadingResult.Failed(error))
            }
            null
        }

    @VisibleForTesting
    internal fun convertToTweets(searchedTweetsResponse: GetSearchedTweetsResponse): List<Tweet> {
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
