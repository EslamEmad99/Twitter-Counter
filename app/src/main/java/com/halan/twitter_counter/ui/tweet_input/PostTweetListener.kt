package com.halan.twitter_counter.ui.tweet_input

interface PostTweetListener {
    fun onPostTweetSuccess()
    fun onPostTweetFail(message: String)
}