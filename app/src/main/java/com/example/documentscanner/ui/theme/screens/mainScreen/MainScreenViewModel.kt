package com.example.documentscanner.ui.theme.screens.mainScreen

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

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
}