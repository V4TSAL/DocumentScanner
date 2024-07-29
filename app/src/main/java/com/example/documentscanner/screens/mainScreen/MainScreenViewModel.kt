package com.example.documentscanner.screens.mainScreen

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.util.Log
import androidx.compose.animation.fadeIn
import androidx.core.content.FileProvider
import androidx.core.net.toUri
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.documentscanner.localStorage.AppPreferences
import com.example.documentscanner.network.ApiRepository
import com.example.documentscanner.network.ApiStatus
import com.example.documentscanner.network.FileInformation
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.File
import javax.inject.Inject
val isTokenValid = MutableLiveData(true)
@HiltViewModel
class MainScreenViewModel @Inject constructor(private val apiRepo: ApiRepository,private val sharedPref: AppPreferences) : ViewModel(){
    private val _documentInformation  = MutableLiveData<List<FileInformation>>()
    val documentInformation : LiveData<List<FileInformation>> = _documentInformation
    var pdfFileId = ""
    var pdfImageId = ""
    var showLoader = MutableLiveData(false)
    fun addFile(fileInfo : FileInformation){
        val tempArray = arrayListOf<FileInformation>()
        if(documentInformation.value!!.isNotEmpty()){
            documentInformation.value!!.forEach {
                tempArray.add(it)
            }
            tempArray.add(fileInfo)
        }
        else{
            tempArray.add(fileInfo)
        }
        _documentInformation.value = tempArray
    }
    fun uploadImage(file:File, onSuccess : (fileName : String)->Unit){
        val requestFile = file.asRequestBody("multipart/form-data".toMediaTypeOrNull())
        val filePart = MultipartBody.Part.createFormData("file", file.name, requestFile)
        val category = "file".toRequestBody("text/plain".toMediaTypeOrNull())
        viewModelScope.launch {
//            _loading.value = true
            showLoader.value = true
            val result = apiRepo.upload(filePart, category)
            when (result) {
                is ApiStatus.Success -> {
                    onSuccess(result.data.message)
                }

                is ApiStatus.Error -> {
                    showLoader.value = false
                }
            }
//            _loading.value = false
        }

    }
    fun storeFile(fileName : String,pdfImageId : String){
        viewModelScope.launch {
//            showLoader.value = true
            val result = apiRepo.storeFile(fileName,pdfImageId)
            when(result){
                is ApiStatus.Success -> {
//                    val tempData = FileModel(file = , pdfUri = result.data.toUri(), imageUri = null )
                    _documentInformation.value = result.data.userFiles
                }
                is ApiStatus.Error -> {
                    Log.d("ERROR BLOCK CHECK", "getFile: ${result.apiError}")
                }
            }
            showLoader.value = false
        }
    }
    fun getUserFile(){
        viewModelScope.launch {
            showLoader.value = true
            val result = apiRepo.getFile()
            when(result){
                is ApiStatus.Success -> {
//                    val tempData = FileModel(file = , pdfUri = result.data.toUri(), imageUri = null )
                    _documentInformation.value = result.data.userFiles
                }
                is ApiStatus.Error -> {
                    Log.d("ERROR BLOCK CHECK", "getFile: ${result.apiError}")
                }
            }
            showLoader.value = false
        }
    }
    fun logout(){
        sharedPref.clearPreferences()
    }
}