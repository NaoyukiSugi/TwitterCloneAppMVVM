package com.example.twitterminiapp.presentation.adapter

import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.RequestManager
import com.example.twitterminiapp.data.model.Tweet
import com.example.twitterminiapp.databinding.ViewTweetBinding

class TwitterViewHolder(
    private val binding: ViewTweetBinding,
    private val requestManager: RequestManager = Glide.with(binding.root),
    private val onUserIconClickListener: ((Tweet) -> Unit)?
) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(tweet: Tweet) {
        binding.apply {
            requestManager.load(tweet.user.profileImageUrlHttps).into(userIcon)
            userName.text = tweet.user.name
            userScreenName.text = tweet.user.screenName
            userDescription.text = tweet.text
            userIcon.setOnClickListener {
                onUserIconClickListener?.let { it(tweet) }
            }
        }
    }
}
