package com.example.documentscanner

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.documentscanner.globals.userId
import com.example.documentscanner.localStorage.AppPreferences
import com.example.documentscanner.screens.LoginOrSignup
import com.example.documentscanner.screens.LoginScreen
import com.example.documentscanner.ui.theme.DocumentScannerTheme
import com.example.documentscanner.screens.StyledText
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import javax.inject.Inject

@AndroidEntryPoint
class SplashActivity : AppCompatActivity() {
    private val splashViewModel: SplashViewModel by viewModels()

    @Inject
    lateinit var appPreferences: AppPreferences
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val showLoginScreen = splashViewModel.showLoginScreen.observeAsState(false)
            val showLoader = splashViewModel.showLoader.observeAsState(false)
            DocumentScannerTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    if (!showLoginScreen.value) {
                        Greeting {
                            if (appPreferences.getToken().isNotEmpty()) {
                                userId = appPreferences.getToken()
                                goToHomeScreen()
                            } else {
                                splashViewModel.showLoginScreen.value = true
                            }
                        }
                    } else {
                        Box(modifier = Modifier.fillMaxSize()) {
                            LoginOrSignup(login = { username, password ->
                                splashViewModel.login(username = username, password = password) {
                                    goToHomeScreen()
                                }
                            }, register = { username, password ->
                                splashViewModel.signup(username = username, password = password){
                                    goToHomeScreen()
                                }
                            })
                            if(showLoader.value){
                                Box(modifier = Modifier
                                    .clip(CircleShape)
                                    .background(Color.White)
                                    .align(alignment = Alignment.Center)
                                ){
                                    CircularProgressIndicator(color = Color.Black,modifier = Modifier.align(Alignment.Center).padding(2.dp))
                                }                            }
                        }

                    }

                }
            }
        }
    }

    private fun goToHomeScreen() {
        val intent = Intent(this, MainActivity::class.java)
        this.startActivity(intent)
        finish()
    }
}

@Composable
fun Greeting(navigateToNewScreen: () -> Unit) {
    LaunchedEffect(key1 = 1) {
        delay(1500)
        navigateToNewScreen()
    }
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        StyledText(text = " Scanner")
    }
}
