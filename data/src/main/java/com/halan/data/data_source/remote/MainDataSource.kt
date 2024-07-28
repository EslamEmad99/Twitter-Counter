package com.halan.data.data_source.remote

import com.halan.domain.model.BaseResponse
import com.halan.domain.model.PostTweetResponse

/**
 * A data source is needed to abstract the source of data from the domain and presentation layers.
 * It allows the application to fetch data from various sources such as a local database, network,
 * or remote API without tightly coupling these implementations to the domain logic.
 * By separating the data source from the domain logic, we achieve better modularity and
 * maintainability, making it easier to switch or extend data sources in the future.
 * **/
interface MainDataSource {

    suspend fun postTweet(tweet: String): BaseResponse<PostTweetResponse>
}