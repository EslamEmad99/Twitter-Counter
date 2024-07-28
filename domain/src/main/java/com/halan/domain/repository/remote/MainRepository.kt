package com.halan.domain.repository.remote

import com.halan.domain.model.BaseResponse
import com.halan.domain.model.PostTweetResponse
import com.halan.domain.util.DataState
import kotlinx.coroutines.flow.Flow

/**
 * A repository acts as a mediator between the data sources (e.g., local database, network) and the domain layer.
 * It abstracts the logic for accessing and managing data, providing a clean interface for the domain layer
 * to interact with data without being concerned about the underlying data sources.
 * Repositories help centralize data access logic, promote code reusability, and improve testability by
 * decoupling the domain layer from specific data source implementations.
 * **/
interface MainRepository {

    suspend fun postTweet(tweet: String): Flow<DataState<BaseResponse<PostTweetResponse>>>
}