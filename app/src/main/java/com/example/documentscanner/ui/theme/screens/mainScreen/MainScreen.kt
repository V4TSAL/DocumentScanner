package com.example.documentscanner.ui.theme.screens.mainScreen

import android.app.Activity
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
import com.google.mlkit.vision.documentscanner.GmsDocumentScannerOptions
import com.google.mlkit.vision.documentscanner.GmsDocumentScanning
import com.google.mlkit.vision.documentscanner.GmsDocumentScanningResult

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(activity: Activity,viewModel: MainScreenViewModel) {
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
                viewModel.addFile(fileInfo = FileModel(file = pdfUri!!.toFile(), imageUri = imageUri!!))
                Log.d("FILE INFORMATION ", "MainScreen: ${viewModel.documentInformation.value}")
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
    val files = viewModel.documentInformation.observeAsState().value
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
                if(!files.isNullOrEmpty()){
                    LazyVerticalGrid(columns = GridCells.Fixed(2)){
                        items(files.toList()){ fileInfo->
                            GridViewItems(item = fileInfo) {

                            }
                        }
                    }
                }
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
fun GridViewItems(item : FileModel,itemOnTap: ()-> Unit) {
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
                itemOnTap()
            }
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp)
        ) {
            AsyncImage(
                model  = item.imageUri,contentDescription = "Image")
//            Text(text = item.name, textAlign = TextAlign.Center,style = TextStyle(fontWeight = FontWeight(700)),overflow = TextOverflow.Ellipsis, maxLines = 2)
        }
    }
}