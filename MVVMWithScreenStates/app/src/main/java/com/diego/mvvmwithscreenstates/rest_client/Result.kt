package com.diego.mvvmwithscreenstates.rest_client

import com.diego.mvvmwithscreenstates.model.Task
import java.io.IOException

sealed class Result<out T : Any> {
    data class Success<out T : Any>(val data: T) : Result<T>()
    data class Error(val exception: Exception) : Result<Nothing>()
}

suspend fun <T : Any> safeApiCall(call: suspend () -> Result<T>, errorMessage: String): Result<T> = try {
    call.invoke()
} catch (e: Exception) {
    Result.Error(IOException(errorMessage, e))
}

suspend fun loadTasks() = safeApiCall(
    { getTasks() },
    "Error occurred"
)

suspend fun getTasks(): Result<List<Task>> {
//    val response = Service.create().listTasks().awaitResponse()
//    if (response.isSuccessful)
//        return Result.Success(response.body()!!)
    return Result.Error(IOException("Error occurred during fetching movies!"))
}