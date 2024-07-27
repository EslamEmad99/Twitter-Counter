package com.halan.domain.use_case.main

import com.halan.domain.use_case.util.Constants.MAX_TWEET_LENGTH
import com.halan.domain.use_case.util.Mockable
import javax.inject.Inject

@Mockable
class ValidateTweetLengthUseCase @Inject constructor() {

    operator fun invoke(tweetLength: Int): Boolean {
        return tweetLength in 1..MAX_TWEET_LENGTH
    }
}