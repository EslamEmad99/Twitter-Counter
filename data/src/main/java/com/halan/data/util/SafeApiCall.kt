package com.halan.data.util

import com.halan.data.util.FailRequestCode.BLOCKED
import com.halan.data.util.FailRequestCode.EXCEPTION
import com.halan.data.util.FailRequestCode.FAIL
import com.halan.data.util.FailRequestCode.UN_AUTH
import com.halan.data.util.NetworkConstants.NETWORK_TIMEOUT
import com.google.gson.Gson
import com.halan.domain.exceptions.NetworkExceptions
import com.halan.domain.model.BaseResponse
import com.halan.domain.util.DataState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.TimeoutCancellationException
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.withTimeout
import retrofit2.HttpException
import java.io.IOException
import java.net.UnknownHostException

suspend fun <T> safeApiCall(
    apiCall: suspend () -> T
): Flow<DataState<T>> = flow {
    withTimeout(NETWORK_TIMEOUT) {
        val response = apiCall.invoke()
        emit(handleSuccess(response))
    }
}.onStart {
    emit(DataState.Loading)
}.catch {
    emit(handleError(it))
}.flowOn(Dispatchers.IO)

fun <T> handleSuccess(response: T): DataState<T> {
    if (response != null) return DataState.Success(response)
    return DataState.Error(NetworkExceptions.UnknownException)
}

fun <T> handleError(it: Throwable): DataState<T> {
    it.printStackTrace()
    return when (it) {
        is TimeoutCancellationException -> {
            DataState.Error(NetworkExceptions.TimeoutException)
        }

        is UnknownHostException -> {
            DataState.Error(NetworkExceptions.ConnectionException)
        }

        is IOException -> {
            DataState.Error(NetworkExceptions.UnknownException)
        }

        is HttpException -> {
            DataState.Error(convertErrorBody(it))
        }

        else -> {
            DataState.Error(NetworkExceptions.UnknownException)
        }
    }
}

private fun convertErrorBody(throwable: HttpException): Exception {
    val errorBody = throwable.response()?.errorBody()?.charStream()
    val response = Gson().fromJson(errorBody, BaseResponse::class.java)
    return when (throwable.code()) {
        FAIL -> NetworkExceptions.CustomException(response.msg)
        UN_AUTH, BLOCKED -> NetworkExceptions.AuthorizationException
        EXCEPTION -> NetworkExceptions.ServerException
        else -> NetworkExceptions.UnknownException
    }
}