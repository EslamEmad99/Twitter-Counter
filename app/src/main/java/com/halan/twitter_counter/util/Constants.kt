package com.halan.twitter_counter.util

object ResponseStatus {
    const val SUCCESS = "success"
    const val NEED_ACTIVATE = "needActive"
    const val NEED_APPROVAL = "needApproval"
    const val FAILED = "fail"
    const val ACTIVE = "active"
    const val NOT_ACTIVE = "waitingApproval"
    const val BLOCK = "blocked"
    const val PENDING = "pending"
    const val UN_AUTH = "unauthenticated"
    const val EXCEPTION = "exception"
}