package com.example.twitterminiapp.presentation.dataBinding

import androidx.databinding.BindingAdapter
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.twitterminiapp.presentation.viewmodel.TwitterViewModel

object BindingAdapters {

    @BindingAdapter("android:onRefresh")
    @JvmStatic
    fun setSwipeRefreshLayoutOnRefreshListener(
        view: SwipeRefreshLayout,
        viewModel: TwitterViewModel
    ) {
        view.setOnRefreshListener {
            viewModel.refresh()
        }
    }
}
