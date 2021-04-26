package com.example.twitterminiapp.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.VisibleForTesting
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.twitterminiapp.data.model.Tweet
import com.example.twitterminiapp.databinding.ViewTweetBinding

class HomeAdapter : ListAdapter<Tweet, TwitterViewHolder>(DIFF_CALLBACK) {

    @VisibleForTesting
    internal var onUserIconClickListener: ((Tweet) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TwitterViewHolder {
        val binding = createViewTweetBinding(parent)
        return createTwitterViewHolder(binding)
    }

    override fun onBindViewHolder(holder: TwitterViewHolder, position: Int) {
        val tweet = getTweet(position)
        holder.bind(tweet)
    }

    fun setOnUserIconClickListener(listener: (Tweet) -> Unit) {
        onUserIconClickListener = listener
    }

    @VisibleForTesting
    internal fun createViewTweetBinding(parent: ViewGroup): ViewTweetBinding =
        ViewTweetBinding.inflate(LayoutInflater.from(parent.context), parent, false)

    @VisibleForTesting
    internal fun createTwitterViewHolder(binding: ViewTweetBinding): TwitterViewHolder =
        TwitterViewHolder(binding = binding, onUserIconClickListener = onUserIconClickListener)

    @VisibleForTesting
    internal fun getTweet(position: Int): Tweet = getItem(position)

    companion object {
        @VisibleForTesting
        internal val DIFF_CALLBACK = object : DiffUtil.ItemCallback<Tweet>() {
            override fun areItemsTheSame(oldItem: Tweet, newItem: Tweet): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: Tweet, newItem: Tweet): Boolean {
                return oldItem == newItem
            }
        }
    }
}
