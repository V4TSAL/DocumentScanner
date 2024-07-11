package com.example.documentscanner.screens.pdfViewer

import PdfViewer
import android.net.Uri
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.core.net.toUri

class PdfViewActivity : AppCompatActivity(){
    override fun onCreate(savedInstanceState: Bundle?) {
        val uri : String = intent.getStringExtra("pdfUri")!!
        super.onCreate(savedInstanceState)
        setContent {
            PdfViewer(uri = uri )
        }
    }
}