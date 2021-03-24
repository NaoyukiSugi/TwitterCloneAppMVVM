package com.example.twitterminiapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.example.twitterminiapp.data.util.Resource
import com.example.twitterminiapp.databinding.ActivityMainBinding
import com.example.twitterminiapp.presentation.adapter.TwitterAdapter
import com.example.twitterminiapp.presentation.viewmodel.TwitterViewModel
import com.example.twitterminiapp.presentation.viewmodel.TwitterViewModelFactory
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    @Inject
    lateinit var adapter: TwitterAdapter

    @Inject
    lateinit var viewModelFactory: TwitterViewModelFactory

    private lateinit var viewModel: TwitterViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        viewModel = ViewModelProvider(this, viewModelFactory).get(TwitterViewModel::class.java)
        viewTimeline()
    }

    private fun viewTimeline() {
        viewModel.getTimeline()
        viewModel.tweets.observe(this, { resource ->
            when (resource) {
                is Resource.Success -> {
                    resource.data?.let {
                        adapter.differ.submitList(it)
                    }
                }
                is Resource.Error -> {
                    resource.message?.let {
                        Toast.makeText(
                            this,
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
        binding.tw
    }
}
