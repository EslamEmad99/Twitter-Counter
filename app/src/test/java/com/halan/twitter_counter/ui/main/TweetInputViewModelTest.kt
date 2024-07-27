package com.halan.twitter_counter.ui.main

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.halan.domain.use_case.main.CountTweetCharactersUseCase
import com.halan.domain.use_case.main.PostTweeUseCase
import com.halan.domain.use_case.main.ValidateTweetLengthUseCase
import com.halan.twitter_counter.ui.tweet_input.TweetInputViewModel
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.kotlin.whenever

@RunWith(MockitoJUnitRunner::class)
class TweetInputViewModelTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var countTweetCharactersUseCase: CountTweetCharactersUseCase

    @Mock
    private lateinit var validateTweetLengthUseCase: ValidateTweetLengthUseCase

    @Mock
    private lateinit var postTweetUseCase: PostTweeUseCase

    private lateinit var viewModel: TweetInputViewModel

    @Before
    fun setUp() {
        viewModel = TweetInputViewModel(
            countTweetCharactersUseCase = countTweetCharactersUseCase,
            validateTweetLengthUseCase = validateTweetLengthUseCase,
            postTweetUseCase = postTweetUseCase,
        )
    }

    @Test
    fun `test empty tweet`() = runTest {
        val tweet = "            "
        val tweetLength = 0

        whenever(countTweetCharactersUseCase(tweet)).thenReturn(tweetLength)
        whenever(validateTweetLengthUseCase(tweetLength)).thenReturn(false)

        viewModel.countTweetLength(tweet)

        assertEquals(tweetLength, viewModel.tweetLength.value)
        assertFalse(viewModel.isValidTweetLength.value)
    }

    @Test
    fun `test valid normal text`() = runTest {
        val tweet = "This is a valid tweet."
        val tweetLength = 22

        whenever(countTweetCharactersUseCase(tweet)).thenReturn(tweetLength)
        whenever(validateTweetLengthUseCase(tweetLength)).thenReturn(true)

        viewModel.countTweetLength(tweet)

        assertEquals(tweetLength, viewModel.tweetLength.value)
        assertTrue(viewModel.isValidTweetLength.value)
    }

    @Test
    fun `test valid normal text with emojis`() = runTest {
        val tweet = "This is a tweet with emojis 😊😊"
        val tweetLength = 28 // Adjust according to how emojis are counted

        whenever(countTweetCharactersUseCase(tweet)).thenReturn(tweetLength)
        whenever(validateTweetLengthUseCase(tweetLength)).thenReturn(true)

        viewModel.countTweetLength(tweet)

        assertEquals(tweetLength, viewModel.tweetLength.value)
        assertTrue(viewModel.isValidTweetLength.value)
    }

    @Test
    fun `test valid normal text with links`() = runTest {
        val tweet = "Check this out: https://example.com"
        val tweetLength = 39

        whenever(countTweetCharactersUseCase(tweet)).thenReturn(tweetLength)
        whenever(validateTweetLengthUseCase(tweetLength)).thenReturn(true)

        viewModel.countTweetLength(tweet)

        assertEquals(tweetLength, viewModel.tweetLength.value)
        assertTrue(viewModel.isValidTweetLength.value)
    }

    @Test
    fun `test valid normal text with Japanese characters`() = runTest {
        val tweet = "これはテストです" // Japanese for "This is a test"
        val tweetLength = 8

        whenever(countTweetCharactersUseCase(tweet)).thenReturn(tweetLength)
        whenever(validateTweetLengthUseCase(tweetLength)).thenReturn(true)

        viewModel.countTweetLength(tweet)

        assertEquals(tweetLength, viewModel.tweetLength.value)
        assertTrue(viewModel.isValidTweetLength.value)
    }

    @Test
    fun `test invalid normal big text`() = runTest {
        val tweet = "a".repeat(281) // Tweet length exceeds the limit
        val tweetLength = 281

        whenever(countTweetCharactersUseCase(tweet)).thenReturn(tweetLength)
        whenever(validateTweetLengthUseCase(tweetLength)).thenReturn(false)

        viewModel.countTweetLength(tweet)

        assertEquals(tweetLength, viewModel.tweetLength.value)
        assertFalse(viewModel.isValidTweetLength.value)
    }
}