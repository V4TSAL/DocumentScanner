package com.example.documentscanner.screens.mainScreen

import android.app.Activity
import android.app.DatePickerDialog
import android.content.Intent
import android.net.Uri
import android.util.Log
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.IntentSenderRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.core.net.toFile
import coil.compose.AsyncImage
import com.example.documentscanner.Greeting
import com.example.documentscanner.R
import com.example.documentscanner.globals.baseUrl
import com.example.documentscanner.network.FileInformation
import com.example.documentscanner.screens.pdfViewer.PdfViewActivity
import com.google.firebase.components.BuildConfig
import com.google.mlkit.vision.documentscanner.GmsDocumentScannerOptions
import com.google.mlkit.vision.documentscanner.GmsDocumentScanning
import com.google.mlkit.vision.documentscanner.GmsDocumentScanningResult
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(activity: Activity,viewModel: MainScreenViewModel) {
    viewModel.getUserFile()
    val scannerLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.StartIntentSenderForResult(),
        onResult = { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                var imageUri: Uri? = null
                var pdfUri: Uri? = null
                val result1 =
                    GmsDocumentScanningResult.fromActivityResultIntent(result.data)
                result1?.pages?.let { pages ->
                    for (page in pages) {
                        imageUri = pages[0].imageUri
                    }
                }
                result1?.pdf?.let { pdf ->
                    pdfUri = pdf.uri
                    pdfUri!!.toFile()
                    val pageCount = pdf.pageCount
                }
                Log.d("FILE INFORMATION ", "MainScreen: ${viewModel.documentInformation.value}")
                viewModel.uploadImage(pdfUri!!.toFile()){pdfId->
                    viewModel.pdfFileId = pdfId
                    viewModel.uploadImage(imageUri!!.toFile()){imageId->
                        viewModel.pdfImageId = imageId
                        viewModel.storeFile(fileName = viewModel.pdfFileId, pdfImageId = viewModel.pdfImageId)
                    }
                }
            }
        })
    val options = GmsDocumentScannerOptions.Builder()
        .setGalleryImportAllowed(false)
        .setResultFormats(
            GmsDocumentScannerOptions.RESULT_FORMAT_JPEG,
            GmsDocumentScannerOptions.RESULT_FORMAT_PDF
        )
        .setScannerMode(GmsDocumentScannerOptions.SCANNER_MODE_FULL)
        .build()
    val scanner = GmsDocumentScanning.getClient(options)
    val context = LocalContext.current
    val files = viewModel.documentInformation.observeAsState(arrayListOf())
    Scaffold(modifier = Modifier
        .fillMaxSize()
        .statusBarsPadding(), topBar = { TopAppBar(title = { Greeting {} }) }) { scaffoldPadding ->
        Box(modifier = Modifier
            .fillMaxSize()
            .padding(top = scaffoldPadding.calculateTopPadding())) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(12.dp)
            ) {
                if(files.value.isNotEmpty()){
                    LazyVerticalGrid(columns = GridCells.Fixed(2)){
                        items(files.value){ fileInfo->
                            GridViewItems(item = fileInfo) {fileInformation->
                                val intent = Intent(context,PdfViewActivity::class.java)
                                intent.putExtra("pdfUri",fileInformation.pdfId)
                                context.startActivity(intent)
//                                val shareIntent = Intent().apply {
//                                    action = Intent.ACTION_SEND
//                                    type = "application/pdf"
//                                    putExtra(Intent.EXTRA_STREAM, fileInformation.pdfUri.toFile())
//                                    addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
//                                }
//                                context.startActivity(Intent.createChooser(shareIntent, "Share PDF"))
//                                viewModel.sharePdf(context = context,file = fileInfo.file)
                            }
                        }
                    }
                }
            }
            if(files.value.isEmpty()){
                Text(text = "No files found tap on the plus icon to start", modifier = Modifier.align(Alignment.Center))
            }
            FloatingActionButton(onClick = {
                scanner.getStartScanIntent(activity)
                    .addOnSuccessListener {scannerIntent->
                        scannerLauncher.launch(IntentSenderRequest.Builder( scannerIntent).build())

                    }
                    .addOnFailureListener {
                        Toast.makeText(context,"Something went wrong", Toast.LENGTH_SHORT).show()
                    }
            },modifier   = Modifier
                .align(Alignment.BottomEnd)
                .padding(16.dp)) {
                Icon(Icons.Filled.Add, contentDescription = "Scan document")
            }
        }

    }
}

@Composable
fun GridViewItems(item : FileInformation, itemOnTap: (fileModel: FileInformation)-> Unit) {
    Box(
        modifier = Modifier
            .padding(start = 8.dp, end = 8.dp, top = 8.dp, bottom = 8.dp)
//            .width(140.dp)
            .height(220.dp)
            .clip(RoundedCornerShape(8.dp))
            .background(color = Color.White)
            .border(
                width = 1.dp,
                color = Color(0xFFD9D9D9),
                shape = RoundedCornerShape(8.dp)
            )
            .clickable {
                itemOnTap(item)
            }
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp)
        ) {
            AsyncImage(
                model  = "$baseUrl/api/getFile/${item.pdfImageId}",contentDescription = "Image",Modifier
                    .padding(12.dp)
                    .height(120.dp))
            Text(text = item.pdfId!!, textAlign = TextAlign.Center,style = TextStyle(fontWeight = FontWeight(700)),overflow = TextOverflow.Ellipsis, maxLines = 2)
        }
    }
}