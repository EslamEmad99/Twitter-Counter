package com.halan.domain.use_case.main

import com.halan.domain.use_case.util.Mockable
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

@Mockable
class PostTweeUseCase @Inject constructor() {

    suspend operator fun invoke(tweet: String) = flow {
        emit(false)
    }
}