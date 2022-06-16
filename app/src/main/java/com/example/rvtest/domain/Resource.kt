package com.example.rvtest.domain

import kotlinx.coroutines.flow.*
import timber.log.Timber

sealed interface Resource<out T> {
    companion object {
        inline fun <ResultType, RequestType> networkBoundResource(
            crossinline query: () -> Flow<ResultType>,
            crossinline fetch: suspend () -> RequestType,
            crossinline saveFetchResult: suspend (RequestType) -> Unit,
            crossinline onFetchFailed: (Throwable) -> String = { it.message.orEmpty() },
            shouldFetch: Boolean = true
        ) = flow {
            emit(Loading(null))

            val data = query().first()

            if (shouldFetch) {
                emit(Loading(data))
                saveFetchResult(fetch())
            }

            val result = query().map { Success(it) }
            emitAll(result)
        }.catch { throwable ->
            Timber.e(throwable)

            val message = onFetchFailed(throwable)
            val result = query().map { Failure(it, message) }

            emitAll(result)
        }
    }
}

data class Success<out T>(val data: T) : Resource<T>
data class Loading<out T>(val data: T?) : Resource<T>
data class Failure<out T>(val data: T?, val message: String) : Resource<T>