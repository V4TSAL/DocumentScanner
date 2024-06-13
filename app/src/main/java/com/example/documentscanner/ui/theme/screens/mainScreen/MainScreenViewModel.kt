package com.example.documentscanner.ui.theme.screens.mainScreen

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MainScreenViewModel : ViewModel(){
    private val _documentInformation  = MutableLiveData<ArrayList<FileModel>?>(null)
    val documentInformation : LiveData<ArrayList<FileModel>?> = _documentInformation
    fun addFile(fileInfo : FileModel){
        var tempArray  = documentInformation.value
        if(tempArray == null){
            tempArray = arrayListOf(fileInfo)
        }
        else{
            tempArray.add(fileInfo)
        }
        _documentInformation.value = tempArray
    }
}