package com.example.twitterminiapp.presentation.dataBinding

import android.widget.SearchView
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
            viewModel.getHomeTimeline()
            swipeRefreshLayout.isRefreshing = false
        }
    }

    @BindingAdapter("onQueryTextListener")
    @JvmStatic
    fun setOnQueryTextListener(
        searchView: SearchView,
        viewModel: TwitterViewModel
    ) {
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                if (query != null) {
                    viewModel.searchQuery = query
                } else {
                    viewModel.searchQuery = ""
                }
                viewModel.invalidata()
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                // do nothing
                return false
            }
        })
    }
}
