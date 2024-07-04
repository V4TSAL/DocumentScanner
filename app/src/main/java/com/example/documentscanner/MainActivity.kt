package com.example.documentscanner

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.documentscanner.ui.theme.DocumentScannerTheme
import com.example.documentscanner.screens.mainScreen.MainScreen
import com.example.documentscanner.screens.mainScreen.MainScreenViewModel

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
enum class LoginFlowScreen{
    LoginScreen,
    MainScreen
}
@Composable
fun LoginFlow(modifier: Modifier = Modifier) {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = LoginFlowScreen.LoginScreen.name, modifier = modifier) {
        composable(route = LoginFlowScreen.LoginScreen.name){

        }
        composable(route = LoginFlowScreen.MainScreen.name){
//            MainScreen()
        }
    }
}



