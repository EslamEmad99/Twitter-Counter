package com.halan.twitter_counter.ui.tweet_input

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.halan.domain.use_case.main.CountTweetCharactersUseCase
import com.halan.domain.use_case.main.PostTweeUseCase
import com.halan.domain.use_case.main.ValidateTweetLengthUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TweetInputViewModel @Inject constructor(
    private val countTweetCharactersUseCase: CountTweetCharactersUseCase,
    private val validateTweetLengthUseCase: ValidateTweetLengthUseCase,
    private val postTweetUseCase: PostTweeUseCase
) : ViewModel() {

    private var _tweetLength = MutableStateFlow(0)
    val tweetLength = _tweetLength.asStateFlow()

    private var _isValidTweetLength = MutableStateFlow(false)
    val isValidTweetLength = _isValidTweetLength.asStateFlow()

    private var _postTweetResponse = MutableStateFlow(false)
    val postTweetResponse = _postTweetResponse.asStateFlow()

    fun countTweetLength(tweet: String) {
        val length = countTweetCharactersUseCase(tweet)
        _tweetLength.value = length
        validateTweetLength(length)
    }

    private fun validateTweetLength(length: Int) {
        _isValidTweetLength.value = validateTweetLengthUseCase(length)
    }

    fun postTweet(tweet: String) {
        viewModelScope.launch {
            postTweetUseCase(tweet = tweet).collect {
                _postTweetResponse.value = it
            }
        }
    }
}
