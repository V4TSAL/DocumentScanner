package com.example.documentscanner.network

import javax.inject.Inject

class ApiRepository @Inject constructor(private val apiInterface:ApiInterface) {
    suspend fun getUser(){
        apiInterface.getUsers()
    }
    suspend fun login(user: User){
        apiInterface.login(user)
    }
}