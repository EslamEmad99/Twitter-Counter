package com.halan.twitter_counter.ui.main

import androidx.lifecycle.ViewModel
import com.halan.domain.use_case.main.CountTweetCharactersUseCase
import com.halan.domain.use_case.main.ValidateTweetLengthUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val countTweetCharactersUseCase: CountTweetCharactersUseCase,
    private val validateTweetLengthUseCase: ValidateTweetLengthUseCase
) : ViewModel() {

    private var _tweetLength = MutableStateFlow(0)
    val tweetLength = _tweetLength.asStateFlow()

    private var _isValidTweetLength = MutableStateFlow(false)
    val isValidTweetLength = _isValidTweetLength.asStateFlow()

    fun countTweetLength(tweet: String) {
        val length = countTweetCharactersUseCase(tweet)
        _tweetLength.value = length
        validateTweetLength(length)
    }

    private fun validateTweetLength(length: Int) {
        _isValidTweetLength.value = validateTweetLengthUseCase(length)
    }
}
