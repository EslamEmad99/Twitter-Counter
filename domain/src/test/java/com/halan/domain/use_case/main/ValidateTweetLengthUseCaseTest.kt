package com.halan.domain.use_case.main

import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

class ValidateTweetLengthUseCaseTest {

    private lateinit var validateTweetLengthUseCase: ValidateTweetLengthUseCase

    @Before
    fun setUp() {
        validateTweetLengthUseCase = ValidateTweetLengthUseCase()
    }

    @Test
    fun `test tweet length zero return false`() {
        val tweetLength = 0
        val result = validateTweetLengthUseCase.invoke(tweetLength)
        assertFalse(result)
    }

    @Test
    fun `test tweet length within limit return true`() {
        val tweetLength = 140
        val result = validateTweetLengthUseCase.invoke(tweetLength)
        assertTrue(result)
    }

    @Test
    fun `test tweet length exactly at max limit return true`() {
        val tweetLength = 280
        val result = validateTweetLengthUseCase.invoke(tweetLength)
        assertTrue(result)
    }

    @Test
    fun `test tweet length exceeding max limit return false`() {
        val tweetLength = 281
        val result = validateTweetLengthUseCase.invoke(tweetLength)
        assertFalse(result)
    }
}