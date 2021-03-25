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
    @SerializedName("description")
    val description: String,
    @SerializedName("profile_image_url_https")
    val profileImageUrlHttps: String,
) : Serializable
