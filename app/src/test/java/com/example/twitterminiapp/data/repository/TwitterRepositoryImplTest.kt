package com.example.twitterminiapp.data.repository

import com.example.twitterminiapp.data.model.GetSearchedTweetsResponse
import com.example.twitterminiapp.data.model.SearchQuery
import com.example.twitterminiapp.data.model.Tweet
import com.example.twitterminiapp.data.repository.dataSource.TwitterRemoteDataSource
import com.example.twitterminiapp.data.util.Result
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.test.*
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.kotlin.doReturn
import org.mockito.kotlin.mock
import org.mockito.kotlin.spy
import org.mockito.kotlin.whenever
import retrofit2.Response
import kotlin.test.assertEquals

internal class TwitterRepositoryImplTest {

    private lateinit var repository: TwitterRepositoryImpl
    private val remoteDataSource: TwitterRemoteDataSource = mock()

    @BeforeEach
    fun setUp() {
        Dispatchers.setMain(TestCoroutineDispatcher())
        repository = spy(TwitterRepositoryImpl(remoteDataSource))
    }

    @AfterEach
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `getTimeline should return Result of GetSearchedTweetsResponse`() {
        runBlockingTest {
            val responseNewGetSearched: Response<List<Tweet>> = mock()
            val responseResult: Result<List<Tweet>> = mock()
            doReturn(responseNewGetSearched).whenever(remoteDataSource).getTimeline()
            doReturn(responseResult)
                .whenever(repository).responseToResourceForOldApi(responseNewGetSearched)

            val result = repository.getTimeline()

            assertEquals(responseResult, result)
        }
    }

    @Test
    fun `getSearchedTimeline should return Result of GetSearchedTweetsResponse`() {
        runBlockingTest {
            val responseResult: Result<GetSearchedTweetsResponse> = mock()
            val searchQuery: SearchQuery = mock()
            val nextToken: String = "nextToken"
            doReturn(responseResult)
                .whenever(repository).getSearchedTimeline(searchQuery, nextToken)

            val result = repository.getSearchedTimeline(searchQuery, nextToken)

            assertEquals(responseResult, result)
        }
    }
}
