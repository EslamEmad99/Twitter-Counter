package com.halan.domain.use_case.main

import com.halan.domain.repository.remote.MainRepository
import com.halan.domain.use_case.util.Mockable
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

@Mockable
class PostTweeUseCase @Inject constructor(
    private val mainRepository: MainRepository
) {

    suspend operator fun invoke(tweet: String) =
        flow { emitAll(mainRepository.postTweet(tweet = tweet)) }
}