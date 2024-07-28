package com.halan.data.remote.end_points

import com.halan.data.util.CommonRemoteEndPointsNames.POST_TWEET
import com.halan.data.util.CommonRemoteEndPointsParameters.TWEET
import com.halan.domain.model.BaseResponse
import com.halan.domain.model.PostTweetResponse
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface MainEndPoints {

    // This API is used to post a tweet to twitter
    // Retrofit is the responsible of the implementation of the API
    @FormUrlEncoded
    @POST(POST_TWEET)
    suspend fun postTweet(
        @Field(TWEET) tweet: String
    ): BaseResponse<PostTweetResponse>
}