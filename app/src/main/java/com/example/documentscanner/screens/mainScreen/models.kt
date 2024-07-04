package com.example.documentscanner.screens.mainScreen

import android.net.Uri
import java.io.File

data class FileModel(
    val file : File,
    val pdfUri : Uri,
    val imageUri : Uri,
)