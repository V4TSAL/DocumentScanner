package com.example.documentscanner.ui.theme.screens.mainScreen

import android.net.Uri
import java.io.File

data class FileModel(
    val file : File,
    val imageUri : Uri,
)