package com.example.twitterminiapp.data.model

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class GetSearchedTweetsResponse(
    @SerializedName("data")
    val tweets: List<GetSearchedTweet>,
    @SerializedName("includes")
    val includes: UserList
) : Serializable
