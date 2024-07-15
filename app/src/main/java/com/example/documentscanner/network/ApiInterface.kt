package com.example.documentscanner.network

import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.Path

interface ApiInterface {
    @GET("/api/users")
    suspend fun getUsers()
    @POST("/api/login")
    suspend fun login(@Body user: User) : LoginResponse
    @POST("/api/register")
    suspend fun register(@Body user: User) : LoginResponse
    @Multipart
    @POST("/api/upload")
    suspend fun addImage(@Part image: MultipartBody.Part, @Part("assetCategory") imageCategory: RequestBody) : Message
    @POST("/api/storeFile")
    suspend fun storeFile(@Body fileName:FileIds): AllFiles

    @GET("/api/getFileForUser/{userId}")
    suspend fun getFile(@Path("userId") userId:String):AllFiles
}