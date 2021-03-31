package com.example.twitterminiapp.presentation.dataBinding

import androidx.databinding.BindingAdapter
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.twitterminiapp.presentation.viewmodel.TwitterViewModel

object BindingAdapters {

    @BindingAdapter("onRefreshListener")
    @JvmStatic
    fun setOnRefreshListener(
        swipeRefreshLayout: SwipeRefreshLayout,
        viewModel: TwitterViewModel
    ) {
        swipeRefreshLayout.setOnRefreshListener {
            viewModel.getTimeline()
            swipeRefreshLayout.isRefreshing = false
        }
    }
}
