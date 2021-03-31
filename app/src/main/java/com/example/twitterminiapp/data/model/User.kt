package com.example.twitterminiapp.data.model

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class User(
    @SerializedName("id")
    val id: Long,
    @SerializedName("name")
    val name: String,
    @SerializedName("screen_name")
    val screenName: String,
    @SerializedName(value = "description", alternate = ["username"])
    val description: String,
    @SerializedName(value = "profile_image_url_https", alternate = ["profile_image_url"])
    val profileImageUrlHttps: String,
) : Serializable
