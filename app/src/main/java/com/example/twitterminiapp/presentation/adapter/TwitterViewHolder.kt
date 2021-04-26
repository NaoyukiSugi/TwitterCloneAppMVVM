package com.example.twitterminiapp.presentation.adapter

import androidx.annotation.VisibleForTesting
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.RequestManager
import com.example.twitterminiapp.data.model.Tweet
import com.example.twitterminiapp.databinding.ViewTweetBinding

class TwitterViewHolder(
    val binding: ViewTweetBinding,
    private val requestManager: RequestManager = Glide.with(binding.root),
    private val onUserIconClickListener: ((Tweet) -> Unit)?
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(tweet: Tweet) {
        binding.tweet = tweet
        binding.homeUserIcon.setOnClickListener {
            onUserIconClickListener?.let { it(tweet) }
        }
    }
}
