package com.example.twitterminiapp.domain.repository

import com.example.twitterminiapp.data.model.Tweet
import com.example.twitterminiapp.data.model.GetSearchedTweetsResponse
import com.example.twitterminiapp.data.model.SearchQuery
import com.example.twitterminiapp.data.util.Result

interface TwitterRepository {
    suspend fun getTimeline(): Result<List<Tweet>>
    suspend fun getSearchedTimeline(
        searchQuery: SearchQuery,
        nextToken: String?
    ): Result<GetSearchedTweetsResponse>
}
