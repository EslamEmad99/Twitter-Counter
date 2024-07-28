package com.halan.twitter_counter.util

import com.halan.domain.exceptions.NetworkExceptions
import com.halan.domain.model.BaseResponse
import com.halan.domain.util.DataState
import com.halan.twitter_counter.R
import com.halan.twitter_counter.util.ResponseStatus.ACTIVE
import com.halan.twitter_counter.util.ResponseStatus.BLOCK
import com.halan.twitter_counter.util.ResponseStatus.EXCEPTION
import com.halan.twitter_counter.util.ResponseStatus.FAILED
import com.halan.twitter_counter.util.ResponseStatus.NEED_ACTIVATE
import com.halan.twitter_counter.util.ResponseStatus.NEED_APPROVAL
import com.halan.twitter_counter.util.ResponseStatus.NOT_ACTIVE
import com.halan.twitter_counter.util.ResponseStatus.PENDING
import com.halan.twitter_counter.util.ResponseStatus.SUCCESS
import com.halan.twitter_counter.util.ResponseStatus.UN_AUTH

interface NetworkExtensionsActions {
    fun onLoad(showLoading: Boolean) {

    }

    fun onCommonError(exceptionMsgId: Int) {

    }

    fun onShowSuccessToast(msg: String?) {

    }

    fun onFail(msg: String?) {

    }

    fun authorizationFail() {

    }

    fun block() {

    }

    fun authorizationNeedActive(msg: String) {

    }

    fun needApproval(msg: String?) {

    }
}

fun <T> DataState<T>.applyCommonSideEffects(
    networkExtensionsActions: NetworkExtensionsActions,
    showLoading: Boolean = true,
    showSuccessToast: Boolean = false,
    cancelNotActive: Boolean = false,
    onSuccess: (T) -> Unit = {},
) {
    when (this) {
        is DataState.Loading -> {
            if (showLoading) networkExtensionsActions.onLoad(true)
        }

        is DataState.Success -> {
            networkExtensionsActions.onLoad(false)
            val msg = (data as BaseResponse<*>).msg
            when ((data as BaseResponse<*>).key) {
                SUCCESS, ACTIVE -> {
                    if (showSuccessToast) networkExtensionsActions.onShowSuccessToast(msg)
                    onSuccess(this.data)
                }

                NEED_ACTIVATE -> {
                    networkExtensionsActions.authorizationNeedActive(msg)
                }

                NEED_APPROVAL -> {
                    networkExtensionsActions.needApproval(msg)
                }

                UN_AUTH -> {
                    networkExtensionsActions.authorizationFail()
                }

                BLOCK -> {
                    networkExtensionsActions.block()
                }

                NOT_ACTIVE, PENDING, FAILED, EXCEPTION -> {
                    if ((data as BaseResponse<*>).key == NOT_ACTIVE && cancelNotActive) {
                        if (showSuccessToast) networkExtensionsActions.onShowSuccessToast(msg)
                        onSuccess(this.data)
                    } else {
                        networkExtensionsActions.onFail(msg)
                    }
                }

                else -> {
                    networkExtensionsActions.onCommonError(R.string.something_went_wrong)
                }
            }
        }

        is DataState.Error -> {
            networkExtensionsActions.onLoad(false)
            handleError(networkExtensionsActions, throwable)
        }

        DataState.Idle -> {
            networkExtensionsActions.onLoad(false)
        }
    }
}

private fun handleError(
    networkExtensionsActions: NetworkExtensionsActions,
    throwable: Throwable,
) {
    when (throwable) {
        is NetworkExceptions.AuthorizationException -> {
            networkExtensionsActions.authorizationFail()
        }

        is NetworkExceptions.NeedActiveException -> {
            networkExtensionsActions.authorizationNeedActive(throwable.msg)
        }

        is NetworkExceptions.NeedApprovalException -> {
            networkExtensionsActions.needApproval(throwable.msg)
        }

        is NetworkExceptions.ConnectionException -> {
            networkExtensionsActions.onCommonError(R.string.no_internet_connection)
        }

        is NetworkExceptions.CustomException -> {
            networkExtensionsActions.onFail(throwable.msg)
        }

        else -> {
            networkExtensionsActions.onCommonError(throwable.getIsCommonException())
        }
    }
}

fun Throwable.getIsCommonException(): Int {
    when (this) {
        is NetworkExceptions.ConnectionException -> {
            return R.string.no_internet_connection
        }

        is NetworkExceptions.NotFoundException -> {
            return R.string.something_went_wrong
        }

        is NetworkExceptions.ServerException -> {
            return R.string.something_went_wrong
        }

        is NetworkExceptions.TimeoutException -> {
            return R.string.no_internet_connection
        }

        is NetworkExceptions.UnknownException -> {
            return R.string.something_went_wrong
        }

        else -> {
            return R.string.something_went_wrong
        }
    }
}