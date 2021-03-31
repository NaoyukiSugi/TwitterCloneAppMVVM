package com.example.twitterminiapp.presentation.dataBinding

import android.widget.SearchView
import androidx.databinding.BindingAdapter
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.twitterminiapp.presentation.viewmodel.TwitterViewModel
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

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

    @BindingAdapter("onQueryTextListener")
    @JvmStatic
    fun setOnQueryTextListener(
        searchView: SearchView,
        viewModel: TwitterViewModel
    ) {
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                viewModel.getSearchedTimeline(query.toString())
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                // do nothing
                return false
            }
        })
    }
}
