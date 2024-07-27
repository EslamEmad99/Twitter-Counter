package com.halan.twitter_counter.ui.main

import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.halan.twitter_counter.R
import com.halan.twitter_counter.databinding.ActivityMainBinding
import com.halan.twitter_counter.ui.tweet_input.PostTweetListener
import com.halan.twitter_counter.ui.tweet_input.TweetInputViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity(), PostTweetListener {

    private val viewModel by viewModels<TweetInputViewModel>()

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initTweetView()
        handleClicks()
    }

    private fun initTweetView() {
        binding.tweetInputView.init(viewModel = viewModel, lifecycleOwner = this, listener = this)
    }

    override fun onPostTweetSuccess() {
        Toast.makeText(this, getString(R.string.tweeted_successfully), Toast.LENGTH_SHORT).show()
    }

    override fun onPostTweetFail(message: String) {

    }

    private fun handleClicks() {
        binding.apply {
            toolbar.ivBack.setOnClickListener {
                onBackPressed()
            }
        }
    }
}