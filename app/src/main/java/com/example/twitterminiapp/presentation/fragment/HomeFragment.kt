package com.example.twitterminiapp.presentation.fragment

import android.os.Bundle
import android.view.LayoutInflater
import androidx.fragment.app.Fragment
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.twitterminiapp.R
import com.example.twitterminiapp.data.util.Result
import com.example.twitterminiapp.databinding.FragmentHomeBinding
import com.example.twitterminiapp.presentation.activity.MainActivity
import com.example.twitterminiapp.presentation.adapter.HomeAdapter
import com.example.twitterminiapp.presentation.viewmodel.TwitterViewModel

class HomeFragment : Fragment(), SwipeRefreshLayout.OnRefreshListener,
    SearchView.OnQueryTextListener {

    private lateinit var binding: FragmentHomeBinding
    private lateinit var homeAdapter: HomeAdapter
    private lateinit var viewModel: TwitterViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_home, container, false)
        viewModel = (activity as MainActivity).viewModel
        initAdapter()
        initBinding()

        binding.swipeRefreshLayout.setOnRefreshListener(this)
        binding.searchView.setOnQueryTextListener(this)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setObserver()

        viewModel.getHomeTimeline()
    }

    override fun onRefresh() {
        viewModel.getHomeTimeline()
        binding.swipeRefreshLayout.isRefreshing = false
    }

    override fun onQueryTextSubmit(query: String?): Boolean {
        if (query != null) {
            viewModel.searchQuery.value = query
            if (findNavController().currentDestination?.id == R.id.homeFragment) {
                findNavController().navigate(R.id.action_homeFragment_to_searchResultFragment)
            }
        } else {
            Toast.makeText(activity, "文字列を入力してください", Toast.LENGTH_LONG).show()
        }
        return false
    }

    override fun onQueryTextChange(newText: String?): Boolean {
        // do nothing
        return false
    }

    private fun initAdapter() {
        homeAdapter = (activity as MainActivity).homeAdapter
        homeAdapter.setOnUserIconClickListener {
            val bundle = Bundle().apply {
                putSerializable("selected_user_icon", it.user)
            }
            findNavController().navigate(R.id.action_homeFragment_to_profileFragment, bundle)
        }
    }

    private fun initBinding() {
        binding.apply {
            lifecycleOwner = viewLifecycleOwner
            homeTimelineRecyclerView.adapter = homeAdapter
            homeTimelineRecyclerView.layoutManager = LinearLayoutManager(activity)
        }
    }

    private fun setObserver() {
        viewModel.apply {

            setHomeTimelineTweetsObserver(viewLifecycleOwner, Observer { result ->
                when (result) {
                    is Result.Success -> {
                        homeAdapter.differ.submitList(result.data)
                    }
                    is Result.Error -> {
                        Toast.makeText(activity, result.message, Toast.LENGTH_LONG).show()
                    }
                    is Result.Loading -> {
                        // TODO()
                    }
                }
            })
        }
    }
}
