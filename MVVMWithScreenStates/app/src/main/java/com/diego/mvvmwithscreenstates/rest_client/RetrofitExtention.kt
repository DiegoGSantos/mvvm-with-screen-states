package com.diego.mvvmwithscreenstates.rest_client

import kotlinx.coroutines.CancellableContinuation
import kotlinx.coroutines.suspendCancellableCoroutine
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import kotlin.coroutines.resume

suspend fun <T : Any?> Call<T>.awaitResponse(success: (response: Response<T>) -> Unit, onError: (Throwable) -> Unit): Response<T> {
    return suspendCancellableCoroutine { continuation ->
        enqueue(object : Callback<T> {
            override fun onResponse(call: Call<T>?, response: Response<T>) {
                if (response.isSuccessful) {
                    success(response)
                } else {
                    onError(Throwable())
                }
            }

            override fun onFailure(call: Call<T>, t: Throwable) {
                if (continuation.isCancelled) return
                onError(t)
            }
        })

        registerOnCompletion(continuation)
    }
}

suspend fun <T : Any?> Call<T>.awaitResult(): Result<Response<T>> {
    return suspendCancellableCoroutine { continuation ->
        enqueue(object : Callback<T> {
            override fun onResponse(call: Call<T>?, response: Response<T>) {
                if (response.isSuccessful) {
                    val result = Result.Success(response)
                    result.let {
                        continuation.resume(it)
                    }
                } else {
                    continuation.resume(Result.Error(Throwable()))
                }
            }

            override fun onFailure(call: Call<T>, t: Throwable) {
                if (continuation.isCancelled) return
                continuation.resume(Result.Error(Throwable()))
            }
        })

        registerOnCompletion(continuation)
    }
}

private fun Call<*>.registerOnCompletion(continuation: CancellableContinuation<*>) {
    continuation.invokeOnCancellation {
        if (continuation.isCancelled)
            try {
                cancel()
            } catch (ex: Throwable) {
                //Ignore cancel exception
            }
    }
}
