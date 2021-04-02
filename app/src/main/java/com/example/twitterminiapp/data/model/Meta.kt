package com.example.twitterminiapp.data.model

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class Meta(
    @SerializedName("next_token")
    val nextToken: String?
) : Serializable
