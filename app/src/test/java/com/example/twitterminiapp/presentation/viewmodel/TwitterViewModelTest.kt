package com.example.twitterminiapp.presentation.viewmodel

import com.example.twitterminiapp.data.util.Result

import android.app.Application
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.paging.PagedList
import com.example.twitterminiapp.data.model.Tweet
import com.example.twitterminiapp.domain.datasource.GetSearchedTweetsDataSource
import com.example.twitterminiapp.domain.datasource.GetSearchedTweetsDataSourceFactory
import com.example.twitterminiapp.domain.datasource.SearchedTweetsDataLoadingResult
import com.example.twitterminiapp.domain.usecase.GetTimelineUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runBlockingTest
import kotlinx.coroutines.test.setMain
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*
import org.mockito.kotlin.*
import java.lang.RuntimeException

internal class TwitterViewModelTest {

    private val app: Application = mock()
    private val getTimelineUseCase: GetTimelineUseCase = mock()
    private val liveDataWithResult: MutableLiveData<Result<List<Tweet>>> = mock()
    private val factory: GetSearchedTweetsDataSourceFactory = mock()
    private val config: PagedList.Config = mock()
    private val searchedTweetsliveData: LiveData<PagedList<Tweet>> = mock()
    private val resultLiveData: MutableLiveData<SearchedTweetsDataLoadingResult> = mock()
    private lateinit var viewModel: TwitterViewModel

    @BeforeEach
    fun setUp() {
        Dispatchers.setMain(TestCoroutineDispatcher())
        viewModel = spy(TwitterViewModel(app, getTimelineUseCase, liveDataWithResult))
        doReturn(config).whenever(viewModel).createPagedListConfig()
        doReturn(searchedTweetsliveData).whenever(viewModel).createSearchedTweetsLiveData(factory, config)
        doReturn(resultLiveData).whenever(factory).getSearchedDataLoadingResultLiveData()
    }

    @AfterEach
    fun tearDown() {
        reset(
                app,
                getTimelineUseCase,
                liveDataWithResult,
                factory,
                config,
                searchedTweetsliveData,
                resultLiveData
        )
        Dispatchers.resetMain()
    }

    @Test
    fun `setUp should create and set livedata`() {
        viewModel.setUp(factory)

        verify(viewModel).createSearchedTweetsLiveData(factory, config)
        verify(factory).getSearchedDataLoadingResultLiveData()
        assertEquals(searchedTweetsliveData, viewModel.searchedTweetsLiveData)
        assertEquals(resultLiveData, viewModel.searchedDataLoadingResultLiveData)
    }

    // region getHomeTimeline
    @Test
    fun `getHomeTimeline should post network error Result when network is not available`() {
        val errorResult: Result.Error<List<Tweet>> = mock()
        doReturn(false).whenever(viewModel).isNetworkAvailable(app)
        doReturn(errorResult).whenever(viewModel).createErrorResult(NETWORK_ERROR)

        viewModel.getHomeTimeline()

        verify(liveDataWithResult).postValue(errorResult)
    }

    @Test
    fun `getHomeTimeline should post tweet Result when network is available and error is not thrown`() {
        runBlockingTest {
            val resultTweetList: Result<List<Tweet>> = mock()
            doReturn(true).whenever(viewModel).isNetworkAvailable(app)
            doReturn(resultTweetList).whenever(getTimelineUseCase).execute()

            viewModel.getHomeTimeline()

            verify(getTimelineUseCase).execute()
            verify(viewModel.homeTimelineTweetsLiveDataWithResult).postValue(resultTweetList)
        }
    }

    @Test
    fun `getHomeTimeline should post caught error Result when network is available and some error is thrown`() {
        runBlockingTest {
            val exception = RuntimeException()
            val errorResult: Result.Error<List<Tweet>> = mock()
            doReturn(true).whenever(viewModel).isNetworkAvailable(app)
            doThrow(exception).whenever(getTimelineUseCase).execute()
            doReturn(errorResult).whenever(viewModel).createErrorResult(any())

            viewModel.getHomeTimeline()

            verify(getTimelineUseCase).execute()
            verify(viewModel.homeTimelineTweetsLiveDataWithResult).postValue(errorResult)
        }
    }
    // endregion

    @Test
    fun `setHomeTimelineTweetsObserver should observe`() {
        val lifecycleOwner: LifecycleOwner = mock()
        val observer: Observer<Result<List<Tweet>>> = mock()

        viewModel.setHomeTimelineTweetsObserver(lifecycleOwner, observer)

        verify(liveDataWithResult).observe(lifecycleOwner, observer)
    }

    @Test
    fun `setSearchedTweetsObserver should observe`() {
        val lifecycleOwner: LifecycleOwner = mock()
        val observer: Observer<PagedList<Tweet>> = mock()
        viewModel.setUp(factory)

        viewModel.setSearchedTweetsObserver(lifecycleOwner, observer)

        verify(searchedTweetsliveData).observe(lifecycleOwner, observer)
    }

    @Test
    fun `setSearchedDataLoadingResultObserver should observe`() {
        val lifecycleOwner: LifecycleOwner = mock()
        val observer: Observer<SearchedTweetsDataLoadingResult> = mock()
        viewModel.setUp(factory)

        viewModel.setSearchedDataLoadingResultObserver(lifecycleOwner, observer)

        verify(resultLiveData).observe(lifecycleOwner, observer)
    }

    @Test
    fun `invalidate should invalidate`() {
        val dataSource: GetSearchedTweetsDataSource = mock()
        val pagedList: PagedList<Tweet> = mock()
        doReturn(pagedList).whenever(searchedTweetsliveData).value
        doReturn(dataSource).whenever(pagedList).dataSource
        viewModel.setUp(factory)

        viewModel.invalidate()

        verify(dataSource).invalidate()
    }

    private companion object {
        const val NETWORK_ERROR = "Internet is not available"
    }
}
