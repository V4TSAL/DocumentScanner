package com.example.documentscanner.localStorage

import android.content.Context
import android.content.SharedPreferences
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class AppPreferences @Inject constructor(@ApplicationContext context : Context){
        private val preferences: SharedPreferences = context.getSharedPreferences("appPrefs",Context.MODE_PRIVATE)
        private val editor = preferences.edit()
        fun setToken(value : String) = editor.putString(PreferenceKeys.TOKEN.name,value).apply()
        fun getToken(): String = preferences.getString(PreferenceKeys.TOKEN.name,"") .toString()
}

enum class PreferenceKeys{
        TOKEN
}