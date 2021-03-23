package com.example.twitterminiapp.domain.repository

import com.example.twitterminiapp.data.model.Tweet
import com.example.twitterminiapp.data.util.Resource

interface TwitterRepository {
    suspend fun getTimeline(): Resource<List<Tweet>>
}
