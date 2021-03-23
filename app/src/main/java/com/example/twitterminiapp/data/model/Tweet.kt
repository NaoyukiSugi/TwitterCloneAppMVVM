package com.example.twitterminiapp.data.model

import java.io.Serializable
import com.google.gson.annotations.SerializedName

data class Tweet(
        @SerializedName("id")
        val id: Long,
        @SerializedName("created_at")
        val createdAt: String,
        @SerializedName("text")
        val text: String,
        @SerializedName("user")
        val user: User
) : Serializable
