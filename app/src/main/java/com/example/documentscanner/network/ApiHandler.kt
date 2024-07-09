package com.example.documentscanner.network


import com.google.android.gms.common.api.Api
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

object ApiHandler {
    suspend fun <T> apiCallHandler(apiCall:suspend ()->T) : ApiStatus<T>{
        return withContext(Dispatchers.IO){
            try {
                ApiStatus.Success(apiCall.invoke())
            }catch (e:Exception){
                ApiStatus.Error(ApiError(message = e.message.toString()))
            }
        }
    }
}
data class ApiError(
    val message : String = "",
    val statusCode : Int?  = null,
)
sealed class ApiStatus <out T>{
    data class Success<T>(val data: T) : ApiStatus<T>()
    data class Error(val apiError: ApiError) : ApiStatus<Nothing>()
}
enum class ApiErrorType {
    ConnectionError,
    InternalServerError,
    ClintError,
    NotHandle

}