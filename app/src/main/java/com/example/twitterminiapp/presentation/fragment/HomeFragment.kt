package com.example.twitterminiapp.presentation.fragment

import android.os.Bundle
import android.view.LayoutInflater
import androidx.fragment.app.Fragment
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.twitterminiapp.R
import com.example.twitterminiapp.data.util.Resource
import com.example.twitterminiapp.databinding.FragmentHomeBinding
import com.example.twitterminiapp.presentation.activity.MainActivity
import com.example.twitterminiapp.presentation.adapter.TwitterAdapter

class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding
    private lateinit var twitterAdapter: TwitterAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_home, container, false)
        binding.apply {
            lifecycleOwner = viewLifecycleOwner
            viewModel = (activity as MainActivity).viewModel
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        twitterAdapter = (activity as MainActivity).twitterAdapter
        twitterAdapter.setOnUserIconClickListener {
            val bundle = Bundle().apply {
                putSerializable("selected_user_icon", it.user)
            }
            findNavController().navigate(R.id.action_homeFragment_to_profileFragment, bundle)
        }
        binding.homeTimelineRecyclerView.apply {
            adapter = twitterAdapter
            layoutManager = LinearLayoutManager(activity)
        }
        viewTimeline()
    }

    private fun viewTimeline() {
        binding.viewModel!!.getTimeline()
        binding.viewModel!!.tweets.observe(viewLifecycleOwner, { resource ->
            when (resource) {
                is Resource.Success -> {
                    resource.data?.let {
                        twitterAdapter.differ.submitList(it)
                    }
                }
                is Resource.Error -> {
                    resource.message?.let {
                        Toast.makeText(
                            activity,
                            "An error occured: $it",
                            Toast.LENGTH_LONG
                        ).show()
                    }
                }
                is Resource.Loading -> {
                    // TODO Progress barを表示
                }
            }
        })

        binding.viewModel!!.searchedTweets.observe(viewLifecycleOwner, { resource ->
            when (resource) {
                is Resource.Success -> {
                    resource.data?.let {
                        twitterAdapter.differ.submitList(it)
                    }
                }
                is Resource.Error -> {
                    resource.message?.let {
                        Toast.makeText(
                            activity,
                            "An error occured: $it",
                            Toast.LENGTH_LONG
                        ).show()
                    }
                }
                is Resource.Loading -> {
                    // TODO Progress barを表示
                }
            }
        })

    }
}
