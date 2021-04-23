package com.example.twitterminiapp.domain.datasource

import androidx.lifecycle.MutableLiveData
import com.example.twitterminiapp.data.model.SearchQuery
import com.example.twitterminiapp.domain.repository.TwitterRepository
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.kotlin.*
import kotlin.test.assertEquals
import kotlin.test.assertSame

internal class GetSearchedTweetsDataSourceFactoryTest {

    private lateinit var factory: GetSearchedTweetsDataSourceFactory
    private val resultLiveData: MutableLiveData<SearchedTweetsDataLoadingResult> = mock()
    private val repository: TwitterRepository = mock()
    private val searchQuery: SearchQuery = mock()
    private val dataSource: GetSearchedTweetsDataSource = mock()

    @BeforeEach
    fun setUp() {
        factory = spy(GetSearchedTweetsDataSourceFactory(resultLiveData, repository, searchQuery))
    }

    @Test
    fun `create should return GetSearchedTweetsDataSource`() {
        doReturn(dataSource).whenever(factory).getGetSearchedTweetsDataSource()

        val result = factory.create()

        assertEquals(dataSource, result)
    }

    @Test
    fun `getSearchedDataLoadingResultLiveData should return searchedDataLoadingResultLiveData`() {
        val result = factory.getSearchedDataLoadingResultLiveData()

        assertSame(resultLiveData, result)
    }
}
