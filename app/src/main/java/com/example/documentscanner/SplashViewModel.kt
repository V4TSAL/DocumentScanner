package com.example.documentscanner

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.documentscanner.localStorage.AppPreferences
import com.example.documentscanner.network.ApiRepository
import com.example.documentscanner.network.User
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(private val apiRepo:ApiRepository,private val appPref:AppPreferences) : ViewModel() {
    val showLoginScreen  = MutableLiveData(false)
    fun login(username: String, password: String){
        viewModelScope.launch{
            apiRepo.login(User(userName = username,password = password))
            appPref.setToken("LOGGEDIN")
        }
    }
}