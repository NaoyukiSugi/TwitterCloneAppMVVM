package com.example.twitterminiapp.domain.datasource

import androidx.paging.PageKeyedDataSource
import com.example.twitterminiapp.data.model.GetSearchedTweet
import com.example.twitterminiapp.data.model.GetSearchedTweetsResponse
import com.example.twitterminiapp.data.util.Resource
import com.example.twitterminiapp.domain.repository.TwitterRepository
import kotlinx.coroutines.*

class GetSearchedTweetsDataSource(
    private val repository: TwitterRepository,
    private val searchQuery: String
) : PageKeyedDataSource<String, GetSearchedTweet>(), CoroutineScope by MainScope() {
    override fun loadInitial(
        params: LoadInitialParams<String>,
        callback: LoadInitialCallback<String, GetSearchedTweet>
    ) {

        launch {
            val response = fetch(null) ?: return@launch
            val data = response.data ?: return@launch
            val tweets = data.tweets
            val nextToken = data.meta.nextToken
            callback.onResult(tweets, null, nextToken)
        }
    }

    override fun loadBefore(
        params: LoadParams<String>,
        callback: LoadCallback<String, GetSearchedTweet>
    ) {
        // do nothing
    }

    override fun loadAfter(
        params: LoadParams<String>,
        callback: LoadCallback<String, GetSearchedTweet>
    ) {
        launch {
            val response = fetch(params.key) ?: return@launch
            val data = response.data ?: return@launch
            val tweets = data.tweets
            val nextToken = data.meta.nextToken
            callback.onResult(tweets, nextToken)
        }
    }

    private suspend fun fetch(nextToken: String?): Resource<GetSearchedTweetsResponse>? =
        withContext(Dispatchers.IO) {
            try {
                repository.getSearchedTimeline(searchQuery = searchQuery, nextToken = nextToken)
            } catch (error: Exception) {
                withContext(Dispatchers.Main) {
                }
            }
            null
        }
}
