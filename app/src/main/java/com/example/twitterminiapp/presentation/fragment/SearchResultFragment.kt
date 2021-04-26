package com.example.twitterminiapp.presentation.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.twitterminiapp.R
import com.example.twitterminiapp.databinding.FragmentSearchResultBinding
import com.example.twitterminiapp.domain.datasource.SearchedTweetsDataLoadingResult
import com.example.twitterminiapp.presentation.activity.MainActivity
import com.example.twitterminiapp.presentation.adapter.SearchResultAdapter
import com.example.twitterminiapp.presentation.viewmodel.TwitterViewModel

class SearchResultFragment : Fragment(), SwipeRefreshLayout.OnRefreshListener {

    private lateinit var binding: FragmentSearchResultBinding
    private lateinit var searchResultAdapter: SearchResultAdapter
    private lateinit var viewModel: TwitterViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_search_result, container, false)
        viewModel = (activity as MainActivity).viewModel
        initAdapter()
        initBinding()

        binding.swipeRefreshLayout.setOnRefreshListener(this)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setObserver()
        viewModel.invalidate()
    }

    override fun onRefresh() {
        viewModel.invalidate()
    }

    private fun initAdapter() {
        searchResultAdapter = (activity as MainActivity).searchResultAdapter
        searchResultAdapter.setOnUserIconClickListener {
            val bundle = Bundle().apply {
                putSerializable("selected_user_icon", it.user)
            }
            findNavController().navigate(
                R.id.action_searchResultFragment_to_profileFragment,
                bundle
            )
        }
    }

    private fun initBinding() {
        binding.apply {
            lifecycleOwner = viewLifecycleOwner
            searchResultTimelineRecyclerView.adapter = searchResultAdapter
            searchResultTimelineRecyclerView.layoutManager = LinearLayoutManager(activity)
        }
    }

    private fun setObserver() {
        viewModel.apply {
            setSearchedTweetsObserver(viewLifecycleOwner, Observer {
                searchResultAdapter.submitList(it)
            })

            setSearchedDataLoadingResultObserver(viewLifecycleOwner, Observer { result ->
                binding.swipeRefreshLayout.isRefreshing = false
                when (result) {
                    SearchedTweetsDataLoadingResult.Found -> {
                        // do nothing
                    }
                    is SearchedTweetsDataLoadingResult.Failed -> {
                        Toast.makeText(activity, result.error.message, Toast.LENGTH_LONG).show()
                    }
                    SearchedTweetsDataLoadingResult.NotFound -> {
                        Toast.makeText(activity, "Tweetが見つかりませんでした", Toast.LENGTH_LONG).show()
                    }
                }
            })
        }
    }
}
