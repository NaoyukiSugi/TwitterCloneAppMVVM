package com.example.twitterminiapp.domain.datasource

import androidx.lifecycle.MutableLiveData
import androidx.paging.PageKeyedDataSource
import com.example.twitterminiapp.data.model.*
import com.example.twitterminiapp.domain.repository.TwitterRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.runBlockingTest
import kotlinx.coroutines.test.setMain
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*
import org.mockito.kotlin.*

internal class GetSearchedTweetsDataSourceTest {

    private lateinit var dataSource: GetSearchedTweetsDataSource
    private val resultLiveData: MutableLiveData<SearchedTweetsDataLoadingResult> = mock()
    private val repository: TwitterRepository = mock()
    private val searchQuery: SearchQuery = mock()

    @BeforeEach
    fun setUp() {
        Dispatchers.setMain(TestCoroutineDispatcher())
        dataSource = spy(GetSearchedTweetsDataSource(resultLiveData, repository, searchQuery))
    }

    // region loadInitial
    @Test
    fun `loadInitial should callback Tweet list when response is not null`() {
        runBlockingTest {

        }
    }

    @Test
    fun `loadInitial should post Found when response tweet is not null`() {
        runBlockingTest {
            val nextToken = "nextToken"
            val meta = Meta(nextToken = nextToken)
            val getSearchedTweetList: List<GetSearchedTweet> = mock()
            val includes: UserList = mock()
            val response = GetSearchedTweetsResponse(
                tweets = getSearchedTweetList,
                includes = includes,
                meta = meta,
            )
            val convertedTweets: List<Tweet> = mock()
            val params: PageKeyedDataSource.LoadInitialParams<String> = mock()
            val callback: PageKeyedDataSource.LoadInitialCallback<String, Tweet> = mock()
            doReturn(response).whenever(dataSource).fetch(nextToken)
            doReturn(convertedTweets).whenever(dataSource).convertToTweets(response)

            dataSource.loadInitial(params, callback)

            verify(resultLiveData).postValue(SearchedTweetsDataLoadingResult.Found)
        }
    }
    // endregion

    @Test
    fun `loadAfter`() {
    }

    @Test
    fun `getCoroutineContext`() {
    }
}
