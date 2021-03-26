package com.example.twitterminiapp

import android.os.Bundle
import android.view.LayoutInflater
import androidx.fragment.app.Fragment
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.twitterminiapp.data.util.Resource
import com.example.twitterminiapp.databinding.FragmentHomeBinding
import com.example.twitterminiapp.presentation.adapter.TwitterAdapter
import com.example.twitterminiapp.presentation.viewmodel.TwitterViewModel

class HomeFragment : Fragment() {

    private lateinit var viewBinding: FragmentHomeBinding
    private lateinit var viewModel: TwitterViewModel
    private lateinit var twitterAdapter: TwitterAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewBinding = FragmentHomeBinding.inflate(inflater, container, false)
        return viewBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = (activity as MainActivity).viewModel
        twitterAdapter = (activity as MainActivity).twitterAdapter
        twitterAdapter.setOnUserIconClickListener {
            val bundle = Bundle().apply {
                putSerializable("selected_user_icon", it.user)
            }
            findNavController().navigate(R.id.action_homeFragment_to_profileFragment, bundle)
        }
        initRecyclerView()
        viewTimeline()
    }

    private fun viewTimeline() {
        viewModel.getTimeline()
        viewModel.tweets.observe(viewLifecycleOwner, { resource ->
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

    private fun initRecyclerView() {
        viewBinding.homeTimelineRecyclerView.apply {
            adapter = twitterAdapter
            layoutManager = LinearLayoutManager(activity)
        }
    }
}
