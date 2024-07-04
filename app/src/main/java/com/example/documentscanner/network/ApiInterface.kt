package com.example.documentscanner.network

import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface ApiInterface {
    @GET("/api/users")
    suspend fun getUsers()
    @POST("/api/login")
    suspend fun login(@Body user: User)
}