package com.example.documentscanner.network

import com.example.documentscanner.network.ApiHandler.apiCallHandler
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.File
import javax.inject.Inject

class ApiRepository @Inject constructor(private val apiInterface:ApiInterface) {
    suspend fun getUser(){
        apiInterface.getUsers()
    }
    suspend fun login(user: User) : ApiStatus<Unit>{
        return  apiCallHandler {
            apiInterface.login(user)
        }
    }
    suspend fun singUp(user: User): ApiStatus<Unit>{
        return apiCallHandler {
            apiInterface.register(user)
        }
    }

    suspend fun upload(
        image: MultipartBody.Part,
        imageCategory: RequestBody
    ): ApiStatus<Unit> {
        return apiCallHandler {
            apiInterface.addImage(image, imageCategory)
        }
    }
    suspend fun getFile():ApiStatus<File>{
        return apiCallHandler {
            apiInterface.getFile()
        }
    }
}