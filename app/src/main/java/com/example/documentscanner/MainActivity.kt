package com.example.documentscanner

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import com.example.documentscanner.ui.theme.DocumentScannerTheme
import com.example.documentscanner.ui.theme.screens.mainScreen.MainScreen
import com.example.documentscanner.ui.theme.screens.mainScreen.MainScreenViewModel

class MainActivity : ComponentActivity() {
    private val viewModel : MainScreenViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            DocumentScannerTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    MainScreen(this@MainActivity,viewModel)
                }
            }
        }
    }
}



