package com.example.twitterminiapp.presentation.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.example.twitterminiapp.R
import com.example.twitterminiapp.databinding.ActivityMainBinding
import com.example.twitterminiapp.domain.datasource.GetSearchedTweetsDataSourceFactory
import com.example.twitterminiapp.domain.repository.TwitterRepository
import com.example.twitterminiapp.presentation.adapter.SearchResultAdapter
import com.example.twitterminiapp.presentation.adapter.HomeAdapter
import com.example.twitterminiapp.presentation.viewmodel.TwitterViewModel
import com.example.twitterminiapp.presentation.viewmodel.TwitterViewModelFactory
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    @Inject
    lateinit var homeAdapter: HomeAdapter

    @Inject
    lateinit var searchResultAdapter: SearchResultAdapter

    @Inject
    lateinit var viewModelFactory: TwitterViewModelFactory

    @Inject
    lateinit var repository: TwitterRepository

    lateinit var viewModel: TwitterViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(this, viewModelFactory).get(TwitterViewModel::class.java)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        viewModel.setUp(
            GetSearchedTweetsDataSourceFactory(
                repository = repository,
                searchQuery = viewModel.searchQuery
            )
        )
    }
}
