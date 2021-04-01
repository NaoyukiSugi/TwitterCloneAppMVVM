package com.example.twitterminiapp.domain.datasource

sealed class SearchedTweetsDataLoadingResult {
    object Found : SearchedTweetsDataLoadingResult()

    object NotFound : SearchedTweetsDataLoadingResult()

    data class Failed(val error: Throwable) : SearchedTweetsDataLoadingResult()
}
