package com.example.twitterminiapp.data.model

import com.google.gson.annotations.SerializedName

data class User(
    @SerializedName("id")
    val id: Int,
    @SerializedName("name")
    val name: String,
    @SerializedName("screen_name")
    val screenName: String,
    @SerializedName("description")
    val description: String,
    @SerializedName("profile_image_url")
    val profileImageUrl: String,
) : java.io.Serializable
