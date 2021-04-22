package com.example.twitterminiapp.domain.datasource

import androidx.lifecycle.MutableLiveData
import androidx.paging.PageKeyedDataSource
import com.example.twitterminiapp.data.model.*
import com.example.twitterminiapp.data.util.Result
import com.example.twitterminiapp.domain.repository.TwitterRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.runBlockingTest
import kotlinx.coroutines.test.setMain
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*
import org.mockito.kotlin.*
import java.lang.NullPointerException

internal class GetSearchedTweetsDataSourceTest {

    private lateinit var dataSource: GetSearchedTweetsDataSource
    private val resultLiveData: MutableLiveData<SearchedTweetsDataLoadingResult> = mock()
    private val repository: TwitterRepository = mock()
    private val searchQuery: SearchQuery = mock()
    val nextToken = "nextToken"


    @BeforeEach
    fun setUp() {
        Dispatchers.setMain(TestCoroutineDispatcher())
        dataSource = spy(GetSearchedTweetsDataSource(resultLiveData, repository, searchQuery))
    }

    // region loadInitial
    @Test
    fun `loadInitial should callback Tweet list when response tweets is not Empty`() {
        runBlockingTest {
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
            doReturn(response).whenever(dataSource).fetch(null)
            doReturn(convertedTweets).whenever(dataSource).convertToTweets(response)

            dataSource.loadInitial(params, callback)

            verify(callback).onResult(convertedTweets, null, nextToken)
        }
    }

    @Test
    fun `loadInitial should not callback Tweet list when response tweets is Empty`() {
        runBlockingTest {
            val params: PageKeyedDataSource.LoadInitialParams<String> = mock()
            val callback: PageKeyedDataSource.LoadInitialCallback<String, Tweet> = mock()
            val meta = Meta(nextToken = nextToken)
            val includes: UserList = mock()
            val response = GetSearchedTweetsResponse(
                tweets = listOf(),
                includes = includes,
                meta = meta,
            )
            doReturn(response).whenever(dataSource).fetch(null)

            dataSource.loadInitial(params, callback)

            verifyZeroInteractions(callback)
        }
    }

    @Test
    fun `loadInitial should not callback Tweet list when response is null`() {
        runBlockingTest {
            val params: PageKeyedDataSource.LoadInitialParams<String> = mock()
            val callback: PageKeyedDataSource.LoadInitialCallback<String, Tweet> = mock()
            doReturn(null).whenever(dataSource).fetch(null)

            dataSource.loadInitial(params, callback)

            verifyZeroInteractions(callback)
        }
    }

    @Test
    fun `loadInitial should post Found when response tweet is not null`() {
        runBlockingTest {
            val params: PageKeyedDataSource.LoadInitialParams<String> = mock()
            val callback: PageKeyedDataSource.LoadInitialCallback<String, Tweet> = mock()
            val meta = Meta(nextToken = nextToken)
            val getSearchedTweetList: List<GetSearchedTweet> = mock()
            val includes: UserList = mock()
            val response = GetSearchedTweetsResponse(
                tweets = getSearchedTweetList,
                includes = includes,
                meta = meta,
            )
            val convertedTweets: List<Tweet> = mock()
            doReturn(response).whenever(dataSource).fetch(null)
            doReturn(convertedTweets).whenever(dataSource).convertToTweets(response)

            dataSource.loadInitial(params, callback)

            verify(resultLiveData).postValue(SearchedTweetsDataLoadingResult.Found)
        }
    }
    // endregion

    // region loadAfter
    @Test
    fun `loadAfter should callback Tweet list when response tweets is not Empty`() {
        runBlockingTest {
            val meta = Meta(nextToken = nextToken)
            val getSearchedTweetList: List<GetSearchedTweet> = mock()
            val includes: UserList = mock()
            val response = GetSearchedTweetsResponse(
                tweets = getSearchedTweetList,
                includes = includes,
                meta = meta,
            )
            val convertedTweets: List<Tweet> = mock()
            val params: PageKeyedDataSource.LoadParams<String> = mock()
            val callback: PageKeyedDataSource.LoadCallback<String, Tweet> = mock()
            doReturn(response).whenever(dataSource).fetch(params.key)
            doReturn(convertedTweets).whenever(dataSource).convertToTweets(response)

            dataSource.loadAfter(params, callback)

            verify(callback).onResult(convertedTweets, nextToken)
        }
    }

    @Test
    fun `loadAfter should not callback Tweet list when response tweets is Empty`() {
        runBlockingTest {
            val meta = Meta(nextToken = nextToken)
            val includes: UserList = mock()
            val response = GetSearchedTweetsResponse(
                tweets = listOf(),
                includes = includes,
                meta = meta,
            )
            val params: PageKeyedDataSource.LoadParams<String> = mock()
            val callback: PageKeyedDataSource.LoadCallback<String, Tweet> = mock()
            doReturn(response).whenever(dataSource).fetch(params.key)

            dataSource.loadAfter(params, callback)

            verifyZeroInteractions(callback)
        }
    }

    // endregion

    // region fetch
    @Test
    fun `fetch should return response when repository return Success Result`() {
        runBlockingTest {
            val response: GetSearchedTweetsResponse = mock()
            val responseResult: Result.Success<GetSearchedTweetsResponse> =
                mock { on { data } doReturn response }
            doReturn(responseResult)
                .whenever(repository).getSearchedTimeline(searchQuery, nextToken)

            val result = dataSource.fetch(nextToken)

            assertEquals(response, result)
        }
    }

    @Test
    fun `fetch should post Failed dataLoadingResult return response when repository throw Exception`() {
        runBlockingTest {
            val error = NullPointerException()
            val failed = SearchedTweetsDataLoadingResult.Failed(error)
            doThrow(error).whenever(repository)
                .getSearchedTimeline(searchQuery, nextToken)

            val result = dataSource.fetch(nextToken)

            verify(resultLiveData).postValue(failed)
            assertNull(result)
        }
    }
    // endregion
}
