package com.example.documentscanner.network

import com.google.gson.annotations.SerializedName

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
    var message : String,
)
data class AllFiles(
    var userFiles : List<FileInformation>
)
data class FileInformation(
    @SerializedName("id") var id : Int? = null,
    @SerializedName("file_id") var fileId : String? = null,
    @SerializedName("file_image_id") var fileImageId : String? = null,
)
data class DeleteFileModel(
    var id : Int
)