package com.example.rvtest.data.source.remote

import kotlinx.coroutines.flow.Flow

class NetworkBoundResource {

    inline operator fun <RequestType, ResultType> invoke(
        crossinline query: () -> Flow<ResultType>,
        crossinline fetch: suspend () -> RequestType,
        crossinline saveFetchResult: suspend (RequestType) -> Unit,
        crossinline onFetchFailed: (Throwable) -> Unit = { Unit },
        shouldFetch: Boolean = true
    ) {

    }
}