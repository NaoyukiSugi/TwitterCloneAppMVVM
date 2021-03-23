package com.example.twitterminiapp.domain.usecase

import com.example.twitterminiapp.domain.repository.TwitterRepository

class GetTimelineUseCase(private val repository: TwitterRepository) {
    suspend fun execute() = repository.getTimeline()
}
