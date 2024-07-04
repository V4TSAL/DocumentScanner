package com.example.documentscanner.screens.mainScreen

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.util.Log
import androidx.core.content.FileProvider
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import java.io.File

class MainScreenViewModel : ViewModel(){
    private val _documentInformation  = MutableLiveData<ArrayList<FileModel>>(arrayListOf())
    val documentInformation : LiveData<ArrayList<FileModel>> = _documentInformation
    fun addFile(fileInfo : FileModel){
        val tempArray = arrayListOf<FileModel>()
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
}