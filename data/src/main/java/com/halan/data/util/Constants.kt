package com.halan.data.util

object Languages {
    const val ARABIC = "ar"
    const val ENGLISH = "en"
}

object NetworkConstants {
    const val LANGUAGE = "lang"
    const val BEARER = "Bearer "
    const val AUTHORIZATION = "Authorization"
    const val NETWORK_TIMEOUT: Long = 600000000
}

object FailRequestCode {
    const val FAIL = 400
    const val UN_AUTH = 419
    const val BLOCKED = 423
    const val EXCEPTION = 500
}

object PreferenceConstants {
    const val PREFERENCE_NAME = "base_preference"
    const val LANGUAGE = "language"
    const val TOKEN = "token"
}

object CommonRemoteEndPointsNames {
    const val POST_TWEET = "post_tweet"
}

object CommonRemoteEndPointsParameters {
    const val TWEET = "tweet"
}