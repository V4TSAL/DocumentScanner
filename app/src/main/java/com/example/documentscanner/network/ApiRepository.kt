package com.example.documentscanner.network

import com.example.documentscanner.globals.userId
import com.example.documentscanner.network.ApiHandler.apiCallHandler
import okhttp3.MultipartBody
import okhttp3.RequestBody
import javax.inject.Inject

class ApiRepository @Inject constructor(private val apiInterface:ApiInterface) {
    suspend fun getUser(){
        apiInterface.getUsers()
    }
    suspend fun login(user: User) : ApiStatus<LoginResponse>{
        return  apiCallHandler {
            apiInterface.login(user)
        }
    }
    suspend fun singUp(user: User): ApiStatus<LoginResponse>{
        return apiCallHandler {
            apiInterface.register(user)
        }
    }

    suspend fun upload(
        image: MultipartBody.Part,
        imageCategory: RequestBody
    ): ApiStatus<Message> {
        return apiCallHandler {
            apiInterface.addImage(image, imageCategory)
        }
    }
    suspend fun storeFile(fileName:String,pdfImageId:String):ApiStatus<AllFiles>{
        return apiCallHandler {
            apiInterface.storeFile(FileIds(pdfId = fileName, pdfImageId = pdfImageId))
        }
    }
    suspend fun getFile():ApiStatus<AllFiles>{
        return apiCallHandler {
            apiInterface.getFile(userId!!)
        }
    }
}