package com.halan.data.repository.remote

import com.halan.data.data_source.remote.MainDataSource
import com.halan.data.util.safeApiCall
import com.halan.domain.repository.remote.MainRepository
import javax.inject.Inject

/**
 * A repository acts as a mediator between the data sources (e.g., local database, network) and the domain layer.
 * It abstracts the logic for accessing and managing data, providing a clean interface for the domain layer
 * to interact with data without being concerned about the underlying data sources.
 * Repositories help centralize data access logic, promote code reusability, and improve testability by
 * decoupling the domain layer from specific data source implementations.
 * **/
class MainRepositoryImpl @Inject constructor(
    private val mainDataSource: MainDataSource
) : MainRepository {
    override suspend fun postTweet(tweet: String) = safeApiCall {
        mainDataSource.postTweet(tweet = tweet)
    }
}