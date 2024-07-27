package com.halan.domain.use_case.main

import com.halan.domain.use_case.util.Constants.MAX_TWEET_LENGTH
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

class CountTweetCharactersUseCaseTest {

    private lateinit var countTweetCharactersUseCase: CountTweetCharactersUseCase

    @Before
    fun setUp() {
        countTweetCharactersUseCase = CountTweetCharactersUseCase()
    }

    @Test
    fun `test empty tweet return 0`() {
        val tweet = ""
        val result = countTweetCharactersUseCase.invoke(tweet)
        assertEquals(0, result)
    }

    @Test
    fun `test tweet with more than 280 normal characters`() {
        val tweet = "a".repeat(281)
        val result = countTweetCharactersUseCase.invoke(tweet)
        assertTrue(result > 280)
    }

    @Test
    fun `test tweet with more than 280 with spaces`() {
        val tweet = "a ".repeat(141) // 141 * 2 = 282 characters (including spaces)
        val result = countTweetCharactersUseCase.invoke(tweet)
        assertTrue(result > MAX_TWEET_LENGTH)
    }

    @Test
    fun `test tweet with emojis`() {
        val tweet = "This is a tweet with emojis 😊😊😊"
        val result = countTweetCharactersUseCase.invoke(tweet)
        assertEquals(31, result) // "This is a tweet with emojis " (28 characters) + 3 emojis
    }

    @Test
    fun `test tweet with other language characters`() {
        val tweet = "これはテストです" // Japanese for "This is a test"
        val result = countTweetCharactersUseCase.invoke(tweet)
        assertEquals(8, result) // Each Japanese character counts as one
    }

    @Test
    fun `test tweet with links`() {
        val tweet = "Check this out: https://example.com"
        val result = countTweetCharactersUseCase.invoke(tweet)
        assertEquals(39, result) // "Check this out: " (16 characters) + URL (23 characters)
    }

    @Test
    fun `test tweet within character limit`() {
        val tweet = "This is a tweet within the character limit."
        val result = countTweetCharactersUseCase.invoke(tweet)
        assertTrue(result <= MAX_TWEET_LENGTH)
    }
}
