package com.example.documentscanner.network

data class User(
    var userName : String,
    var password : String,
)

data class Message(
    var message : String,
)
data class FileIds(
    var pdfId : String,
    var pdfImageId : String
)
data class LoginResponse(
    var message : Int,
)
data class AllFiles(
    var userFiles : List<FileInformation>
)
data class FileInformation(
    var fileName : String? = null,
    var fileUrl  : String? = null ,
    var fileImageUrl : String? = null,
)