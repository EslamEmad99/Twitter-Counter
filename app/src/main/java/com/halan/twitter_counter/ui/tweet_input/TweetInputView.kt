package com.halan.twitter_counter.ui.tweet_input

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.LinearLayout
import androidx.core.widget.doAfterTextChanged
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.halan.domain.use_case.util.Constants.MAX_TWEET_LENGTH
import com.halan.twitter_counter.R
import com.halan.twitter_counter.databinding.ViewTweetInputBinding
import kotlinx.coroutines.launch

class TweetInputView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : LinearLayout(context, attrs, defStyleAttr) {

    private val binding: ViewTweetInputBinding =
        ViewTweetInputBinding.inflate(LayoutInflater.from(context), this, true)

    private lateinit var viewModel: TweetInputViewModel
    private lateinit var lifecycleOwner: LifecycleOwner
    private lateinit var listener: PostTweetListener

    fun init(
        viewModel: TweetInputViewModel,
        lifecycleOwner: LifecycleOwner,
        listener: PostTweetListener
    ) {
        this.viewModel = viewModel
        this.lifecycleOwner = lifecycleOwner
        this.listener = listener

        countTweetLength()
        observeTweetTextLength()
        observeIsValidTweetLength()
        observePostTweet()
        setTweetTextChangeListener()
        handleClicks()
    }

    private fun observeTweetTextLength() {
        lifecycleOwner.lifecycleScope.launch {
            lifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.tweetLength.collect {
                    setTweetLengthText(it)
                }
            }
        }
    }

    private fun setTweetLengthText(length: Int) {
        binding.tvCounter.text = "$length/${MAX_TWEET_LENGTH}"
        binding.tvRemaining.text = (MAX_TWEET_LENGTH - length).toString()
    }

    private fun observeIsValidTweetLength() {
        lifecycleOwner.lifecycleScope.launch {
            lifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.isValidTweetLength.collect {
                    binding.btnPostTweet.isEnabled = it
                }
            }
        }
    }

    private fun observePostTweet() {
        lifecycleOwner.lifecycleScope.launch {
            lifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.postTweetResponse.collect { isSuccess ->
                    if (isSuccess) {
                        listener.onPostTweetSuccess()
                        clearTweetText()
                    } else {
                        listener.onPostTweetFail("Todo")
                    }
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
            btnCopyText.setOnClickListener {
                copyText()
            }
            btnClearText.setOnClickListener {
                clearText()
            }
            btnPostTweet.setOnClickListener {
                viewModel.postTweet(binding.etTweet.text.toString())
            }
        }
    }

    private fun copyText() {
        val text = binding.etTweet.text.toString()
        if (text.isNotBlank()) {
            val clipboard: ClipboardManager =
                context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
            val clip = ClipData.newPlainText("", text)
            clipboard.setPrimaryClip(clip)
        }
    }

    private fun clearText() {
        MaterialAlertDialogBuilder(context)
            .setTitle(context.getString(R.string.warning))
            .setMessage(context.getString(R.string.are_you_sure_you_want_to_clear_the_text))
            .setNegativeButton(context.getString(R.string.cancel)) { _, _ -> }
            .setPositiveButton(context.getString(R.string.confirm)) { _, _ ->
                clearTweetText()
            }.show()
    }

    private fun clearTweetText() {
        binding.etTweet.setText("")
    }
}
