package com.example.twitterminiapp.data.model

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class UserList(
    @SerializedName("users")
    val users: List<User>
) : Serializable
