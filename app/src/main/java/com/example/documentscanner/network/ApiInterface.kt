package com.example.documentscanner.network

import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
import java.io.File

interface ApiInterface {
    @GET("/api/users")
    suspend fun getUsers()
    @POST("/api/login")
    suspend fun login(@Body user: User)
    @POST("/api/register")
    suspend fun register(@Body user: User)
    @Multipart
    @POST("/api/upload")
    suspend fun addImage(@Part image: MultipartBody.Part, @Part("assetCategory") imageCategory: RequestBody)
    @GET("/api/getFile")
    suspend fun getFile():File
}