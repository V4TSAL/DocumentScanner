package com.example.documentscanner.network

import com.example.documentscanner.localStorage.AppPreferences

class AuthTokenManager(private val appPreferences: AppPreferences) {
    fun getToken():String{
        return appPreferences.getToken()
    }
}