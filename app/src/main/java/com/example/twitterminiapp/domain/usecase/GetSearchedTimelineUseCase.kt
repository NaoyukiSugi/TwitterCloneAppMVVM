package com.example.twitterminiapp.domain.usecase

import com.example.twitterminiapp.domain.repository.TwitterRepository

class GetSearchedTimelineUseCase(private val repository: TwitterRepository) {
    suspend fun execute(searchQuery: String, nextToken: String?) =
        repository.getSearchedTimeline(searchQuery, nextToken)
}
