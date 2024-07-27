package com.halan.twitter_counter.ui.main

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.doAfterTextChanged
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.halan.domain.use_case.util.Constants.MAX_TWEET_LENGTH
import com.halan.twitter_counter.R
import com.halan.twitter_counter.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private val viewModel by viewModels<MainViewModel>()

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        countTweetLength()
        observeTweetTextLength()
        observeIsValidTweetLength()
        setTweetTextChangeListener()
        handleClicks()
    }

    private fun observeTweetTextLength() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.tweetLength.collect {
                    setTweetLengthText(length = it)
                }
            }
        }
    }

    private fun setTweetLengthText(length: Int) {
        binding.tvCounter.text = "$length/$MAX_TWEET_LENGTH"
        binding.tvRemaining.text = (MAX_TWEET_LENGTH - length).toString()
    }

    private fun observeIsValidTweetLength() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.isValidTweetLength.collect {
                    binding.btnPostTweet.isEnabled = it
                }
            }
        }
    }

    private fun setTweetTextChangeListener() {
        binding.etTweet.doAfterTextChanged {
            countTweetLength()
        }
    }

    private fun countTweetLength() {
        viewModel.countTweetLength(binding.etTweet.text.toString())
    }

    private fun handleClicks() {
        binding.apply {
            toolbar.ivBack.setOnClickListener {
                onBackPressed()
            }
            btnCopyText.setOnClickListener {
                copyText()
            }
            btnClearText.setOnClickListener {
                clearText()
            }
        }
    }

    private fun copyText() {
        val text = binding.etTweet.text.toString()
        if (text.isNotBlank()) {
            val clipboard: ClipboardManager =
                getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
            val clip = ClipData.newPlainText("", text)
            clipboard.setPrimaryClip(clip)
        }
    }

    private fun clearText() {
        MaterialAlertDialogBuilder(this)
            .setTitle(getString(R.string.warning))
            .setMessage(getString(R.string.are_you_sure_you_want_to_clear_the_text))
            .setNegativeButton(getString(R.string.cancel)) { _, _ -> }
            .setPositiveButton(getString(R.string.confirm)) { _, _ ->
                binding.etTweet.setText("")
            }.show()
    }
}