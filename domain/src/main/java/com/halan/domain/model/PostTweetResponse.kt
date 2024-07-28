package com.halan.domain.model

import com.squareup.moshi.Json

data class PostTweetResponse(
    @Json(name = "is_success") val isSuccess: Boolean,
    @Json(name = "error_message") val errorMessage: String?
)
