package com.example.twitterminiapp.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.twitterminiapp.data.model.Tweet
import com.example.twitterminiapp.databinding.ViewTweetBinding

class TwitterAdapter : RecyclerView.Adapter<TwitterViewHolder>() {

    private var onUserIconClickListener: ((Tweet) -> Unit)? = null

    val differ = AsyncListDiffer(this, DIFF_CALLBACK)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TwitterViewHolder {
        val binding = ViewTweetBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return TwitterViewHolder(
            binding = binding,
            onUserIconClickListener = onUserIconClickListener
        )
    }

    override fun onBindViewHolder(holder: TwitterViewHolder, position: Int) {
        val tweet = differ.currentList[position]
        holder.bind(tweet)
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
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
