package com.example.twitterminiapp.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import com.example.twitterminiapp.data.model.Tweet
import com.example.twitterminiapp.databinding.ViewTweetBinding

class SearchResultAdapter : PagedListAdapter<Tweet, TwitterViewHolder>(DIFF_CALLBACK) {

    private var onUserIconClickListener: ((Tweet) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TwitterViewHolder {
        val binding = ViewTweetBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return TwitterViewHolder(
            binding = binding,
            onUserIconClickListener = onUserIconClickListener
        )
    }

    override fun onBindViewHolder(holder: TwitterViewHolder, position: Int) {
        getItem(position)?.run { holder.bind(this) }
    }

    fun setOnUserIconClickListener(listener: (Tweet) -> Unit) {
        onUserIconClickListener = listener
    }

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<Tweet>() {
            override fun areItemsTheSame(oldItem: Tweet, newItem: Tweet): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: Tweet, newItem: Tweet): Boolean {
                return oldItem == newItem
            }
        }
    }
}
