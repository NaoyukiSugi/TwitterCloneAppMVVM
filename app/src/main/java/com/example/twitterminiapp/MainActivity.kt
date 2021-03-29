package com.example.twitterminiapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
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
    lateinit var twitterAdapter: TwitterAdapter

    @Inject
    lateinit var viewModelFactory: TwitterViewModelFactory

    lateinit var viewModel: TwitterViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(this, viewModelFactory).get(TwitterViewModel::class.java)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}
