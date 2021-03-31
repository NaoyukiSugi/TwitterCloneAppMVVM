package com.example.twitterminiapp.presentation.viewmodel

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.twitterminiapp.domain.usecase.GetSearchedTimelineUseCase
import com.example.twitterminiapp.domain.usecase.GetTimelineUseCase

class TwitterViewModelFactory(
    private val app: Application,
    private val getTimelineUseCase: GetTimelineUseCase,
    private val getSearchedTimelineUseCase: GetSearchedTimelineUseCase
) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return TwitterViewModel(app, getTimelineUseCase, getSearchedTimelineUseCase) as T
    }
}
