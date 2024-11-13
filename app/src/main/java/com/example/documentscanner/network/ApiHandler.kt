package com.example.documentscanner.network


import android.util.Log
import com.example.documentscanner.screens.mainScreen.isTokenValid
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.HttpException

object ApiHandler {
    suspend fun <T> apiCallHandler(apiCall:suspend ()->T) : ApiStatus<T>{
        return withContext(Dispatchers.IO){
            try {
                ApiStatus.Success(apiCall.invoke())
            }catch (e:HttpException){
                if(e.code() == 401){
                    Log.d("API ERROR BLOCK", "apiCallHandler: ")
                    isTokenValid.postValue(false)
                }
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