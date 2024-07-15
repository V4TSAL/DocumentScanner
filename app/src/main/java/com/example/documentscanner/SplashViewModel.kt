package com.example.documentscanner

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.documentscanner.globals.userId
import com.example.documentscanner.localStorage.AppPreferences
import com.example.documentscanner.network.ApiRepository
import com.example.documentscanner.network.ApiStatus
import com.example.documentscanner.network.User
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.File
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(private val apiRepo:ApiRepository,private val appPref:AppPreferences) : ViewModel() {
    val showLoginScreen  = MutableLiveData(false)
    val showLoader  = MutableLiveData(false)
    fun login(username: String, password: String,onSuccess:()->Unit){
        viewModelScope.launch{
            showLoader.value = true
            var result = apiRepo.login(User(userName = username,password = password))
            when(result){
                is ApiStatus.Success -> {
                    appPref.setToken(result.data.message.toString())
                    userId = result.data.message
                    onSuccess()
                }
                is ApiStatus.Error -> {
                    println("Error")
                }
            }
            showLoader.value = false
        }
    }
    fun signup(username: String, password: String){
        viewModelScope.launch{
            apiRepo.singUp(User(userName = username,password = password))
            appPref.setToken("LOGGEDIN")
        }
    }

}