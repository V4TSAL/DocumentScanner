package com.example.documentscanner.screens.mainScreen

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.util.Log
import androidx.core.content.FileProvider
import androidx.core.net.toUri
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
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

@HiltViewModel
class MainScreenViewModel @Inject constructor(private val apiRepo: ApiRepository) : ViewModel(){
    private val _documentInformation  = MutableLiveData<List<FileInformation>>()
    val documentInformation : LiveData<List<FileInformation>> = _documentInformation
    var pdfFileId = ""
    var pdfImageId = ""
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

    fun sharePdf(context: Context, file: File) {
        if (!file.exists()) {
            Log.e("sharePdf", "File does not exist: ${file.path}")
            return
        }

        val uri: Uri = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            FileProvider.getUriForFile(context, "${context.packageName}.provider", file)
        } else {
            Uri.fromFile(file)
        }

        val shareIntent = Intent().apply {
            action = Intent.ACTION_SEND
            type = "application/pdf"
            putExtra(Intent.EXTRA_STREAM, uri)
            addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
        }

        context.startActivity(Intent.createChooser(shareIntent, "Share PDF"))
    }
    fun uploadImage(file:File, onSuccess : (fileName : String)->Unit){
        val requestFile = file.asRequestBody("multipart/form-data".toMediaTypeOrNull())
        val filePart = MultipartBody.Part.createFormData("file", file.name, requestFile)
        val category = "file".toRequestBody("text/plain".toMediaTypeOrNull())
        viewModelScope.launch {
//            _loading.value = true
            val result = apiRepo.upload(filePart, category)
            when (result) {
                is ApiStatus.Success -> {
                    onSuccess(result.data.message)
                }

                is ApiStatus.Error -> {
                }
            }
//            _loading.value = false
        }

    }
    fun storeFile(fileName : String,pdfImageId : String){
        viewModelScope.launch {
            val result = apiRepo.storeFile(fileName,pdfImageId)
        }
    }
    fun getUserFile(){
        viewModelScope.launch {
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
        }
    }
}