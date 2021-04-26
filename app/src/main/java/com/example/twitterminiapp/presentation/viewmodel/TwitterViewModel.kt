package com.example.twitterminiapp.presentation.viewmodel

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import androidx.annotation.VisibleForTesting
import androidx.lifecycle.*
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.example.twitterminiapp.data.model.SearchQuery
import com.example.twitterminiapp.data.model.Tweet
import com.example.twitterminiapp.data.util.Result
import com.example.twitterminiapp.domain.datasource.GetSearchedTweetsDataSourceFactory
import com.example.twitterminiapp.domain.datasource.SearchedTweetsDataLoadingResult
import com.example.twitterminiapp.domain.usecase.GetTimelineUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.lang.Exception

class TwitterViewModel(
        private val app: Application,
        private val getTimelineUseCase: GetTimelineUseCase,
        @VisibleForTesting val homeTimelineTweetsLiveDataWithResult: MutableLiveData<Result<List<Tweet>>>
) : AndroidViewModel(app) {

    constructor(
            app: Application,
            getTimelineUseCase: GetTimelineUseCase,
    ) : this(
            app = app,
            getTimelineUseCase = getTimelineUseCase,
            homeTimelineTweetsLiveDataWithResult = MutableLiveData()
    )

    lateinit var searchedTweetsLiveData: LiveData<PagedList<Tweet>>

    lateinit var searchedDataLoadingResultLiveData: LiveData<SearchedTweetsDataLoadingResult>

    val searchQuery = SearchQuery("")

    fun setUp(
            factory: GetSearchedTweetsDataSourceFactory
    ) {
        searchedTweetsLiveData = createSearchedTweetsLiveData(factory, createPagedListConfig())
        searchedDataLoadingResultLiveData = factory.getSearchedDataLoadingResultLiveData()
    }

    fun getHomeTimeline() = viewModelScope.launch(Dispatchers.IO) {
        if (!isNetworkAvailable(app)) {
            homeTimelineTweetsLiveDataWithResult.postValue(createErrorResult(NETWORK_ERROR))
            return@launch
        }
        try {
            val result = getTimelineUseCase.execute()
            homeTimelineTweetsLiveDataWithResult.postValue(result)
        } catch (e: Exception) {
            homeTimelineTweetsLiveDataWithResult.postValue(createErrorResult(e.message.toString()))
        }
    }

    fun setHomeTimelineTweetsObserver(
            lifecycleOwner: LifecycleOwner,
            observer: Observer<Result<List<Tweet>>>
    ) {
        homeTimelineTweetsLiveDataWithResult.observe(lifecycleOwner, observer)
    }

    fun setSearchedTweetsObserver(
            lifecycleOwner: LifecycleOwner,
            observer: Observer<PagedList<Tweet>>
    ) {
        searchedTweetsLiveData.observe(lifecycleOwner, observer)
    }

    fun setSearchedDataLoadingResultObserver(
            lifecycleOwner: LifecycleOwner,
            observer: Observer<SearchedTweetsDataLoadingResult>
    ) {
        searchedDataLoadingResultLiveData.observe(lifecycleOwner, observer)
    }

    fun invalidate() {
        searchedTweetsLiveData.value?.dataSource?.invalidate()
    }

    @VisibleForTesting
    internal fun createSearchedTweetsLiveData(
            factory: GetSearchedTweetsDataSourceFactory,
            config: PagedList.Config
    ) = LivePagedListBuilder(factory, config).build()

    @VisibleForTesting
    internal fun createPagedListConfig() =
            PagedList.Config.Builder()
                    .setPageSize(GET_SEAECH_TWEETS_SIZE)
                    .setEnablePlaceholders(true)
                    .build()

    @VisibleForTesting
    internal fun isNetworkAvailable(context: Context?): Boolean {
        if (context == null) return false
        val connectivityManager =
                context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            val capabilities =
                    connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork)
            if (capabilities != null) {
                when {
                    capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> {
                        return true
                    }
                    capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> {
                        return true
                    }
                    capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> {
                        return true
                    }
                }
            }
        } else {
            val activeNetworkInfo = connectivityManager.activeNetworkInfo
            if (activeNetworkInfo != null && activeNetworkInfo.isConnected) true
        }
        return false
    }

    @VisibleForTesting
    internal fun createErrorResult(message: String) = Result.Error<List<Tweet>>(message)

    private companion object {
        const val NETWORK_ERROR = "Internet is not available"
        const val GET_SEAECH_TWEETS_SIZE = 10
    }
}
