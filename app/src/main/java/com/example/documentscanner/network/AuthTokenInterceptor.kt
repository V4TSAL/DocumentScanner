package com.example.documentscanner.network

import android.util.Log
import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject

class AuthTokenInterceptor @Inject constructor(private val tokenManager: AuthTokenManager) :Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val orignal = chain.request()
        var request =orignal
        try {
            val token:String = tokenManager.getToken()
            orignal.newBuilder()
            request = if(token.isNotEmpty()){
                Log.d("TOKEN", "intercept: $token")
                orignal.newBuilder()
                    .header("Authorization", token)
                    .method(orignal.method, orignal.body).build()
            }else{
                request
            }
        }catch (t:Throwable){
            Log.e("new request", "Unable to retrieve current user's ID token.", t)
        }
        return chain.proceed(request)
    }
}