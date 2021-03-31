package com.example.twitterminiapp.data.model

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class GetSearchedTweet(
    @SerializedName("id")
    val id: Long,
    @SerializedName("created_at")
    val createdAt: String,
    @SerializedName("text")
    val text: String,
    @SerializedName("author_id")
    val authorId: Long
) : Serializable
