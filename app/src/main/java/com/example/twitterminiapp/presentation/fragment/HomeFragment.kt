package com.example.twitterminiapp.presentation.fragment

import android.os.Bundle
import android.view.LayoutInflater
import androidx.fragment.app.Fragment
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.twitterminiapp.R
import com.example.twitterminiapp.data.util.Result
import com.example.twitterminiapp.databinding.FragmentHomeBinding
import com.example.twitterminiapp.domain.datasource.GetSearchedTweetsDataSourceFactory
import com.example.twitterminiapp.domain.datasource.SearchedTweetsDataLoadingResult
import com.example.twitterminiapp.presentation.activity.MainActivity
import com.example.twitterminiapp.presentation.adapter.TwitterAdapter
import com.example.twitterminiapp.presentation.viewmodel.TwitterViewModel

class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding
    private lateinit var twitterAdapter: TwitterAdapter
    private lateinit var twitterViewModel: TwitterViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_home, container, false)
        twitterViewModel = (activity as MainActivity).viewModel
        initAdapter()
        initBinding()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViewModel()
        twitterViewModel.getHomeTimeline()
    }

    private fun initAdapter() {
        twitterAdapter = (activity as MainActivity).twitterAdapter
        twitterAdapter.setOnUserIconClickListener {
            val bundle = Bundle().apply {
                putSerializable("selected_user_icon", it.user)
            }
            findNavController().navigate(R.id.action_homeFragment_to_profileFragment, bundle)
        }
    }

    private fun initBinding() {
        binding.apply {
            lifecycleOwner = viewLifecycleOwner
            viewModel = twitterViewModel
            homeTimelineRecyclerView.adapter = twitterAdapter
            homeTimelineRecyclerView.layoutManager = LinearLayoutManager(activity)
        }
    }

    private fun initViewModel() {

        twitterViewModel.apply {
            setUp(
                GetSearchedTweetsDataSourceFactory(
                    getSearchedTimelineUseCase = (activity as MainActivity).getSearchedTimelineUseCase,
                    repository = (activity as MainActivity).repository,
                    searchQuery = twitterViewModel.searchQuery
                )
            )

            setHomeTimelineTweetsObserver(viewLifecycleOwner, Observer { result ->
                when (result) {
                    is Result.Success -> {
                        twitterAdapter.differ.submitList(result.data)
                    }
                    is Result.Error -> {
                        Toast.makeText(activity, result.message, Toast.LENGTH_LONG).show()
                    }
                    is Result.Loading -> {
                        // TODO()
                    }
                }
            })

            setSearchedTweetsObserver(viewLifecycleOwner, Observer {
                twitterAdapter.differ.submitList(it)
            })

            setSearchedDataLoadingResultObserver(viewLifecycleOwner, Observer { result ->
                when (result) {
                    SearchedTweetsDataLoadingResult.Found -> {

                    }
                    is SearchedTweetsDataLoadingResult.Failed -> {
                        Toast.makeText(activity, result.error.message, Toast.LENGTH_LONG).show()
                    }
                    SearchedTweetsDataLoadingResult.NotFound -> {
                        // TODO()
                    }
                }
            })
        }
    }
}
