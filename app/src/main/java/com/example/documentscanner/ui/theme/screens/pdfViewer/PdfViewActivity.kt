package com.example.documentscanner.ui.theme.screens.pdfViewer

import PdfViewer
import android.net.Uri
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.core.net.toUri

class PdfViewActivity : AppCompatActivity(){
    override fun onCreate(savedInstanceState: Bundle?) {
        val uri : Uri = intent.getStringExtra("pdfUri")!!.toUri()
        super.onCreate(savedInstanceState)
        setContent {
            PdfViewer(uri = uri )
        }
    }
}