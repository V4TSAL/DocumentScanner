package com.example.documentscanner.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun LoginOrSignup(login: (userName: String, password: String) -> Unit,register: (userName: String, password: String) -> Unit) {
    val showLoginScreen = remember { mutableStateOf(true) }
    if(showLoginScreen.value){
        LoginScreen( { userName, password ->
         login(userName, password)
        }){
            showLoginScreen.value = false
        }
    }
    else{
        SignupScreen ({ userName, password ->
            register(userName, password)
        }){
            showLoginScreen.value = true
        }
    }

}
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginScreen(login: (userName: String, password: String) -> Unit,changeScreen: () -> Unit) {
    var username = remember { mutableStateOf("") }
    var password = remember { mutableStateOf("") }
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceAround
    ) {
        StyledText(text = "Scanner")
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            TextField(value = username.value, onValueChange = {
                username.value = it
            }, placeholder = { Text(text = "Username") })
            Spacer(modifier = Modifier.height(16.dp))
            TextField(value = password.value, onValueChange = {
                password.value = it
            }, placeholder = { Text(text = "Password") })
            Button(onClick = {
                login(username.value, password.value)
            }, content = { Text(text = "Login") })
        }
        Column(modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally){
            Text(text = "Don't have an account?")
            Text("Register", color = Color.Blue,modifier = Modifier.clickable {
              changeScreen()
            })
        }
    }
}
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SignupScreen(login: (userName: String, password: String) -> Unit,changeScreen: () -> Unit) {
    var username = remember { mutableStateOf("") }
    var password = remember { mutableStateOf("") }
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceAround
    ) {
        StyledText(text = "Scanner")
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            TextField(value = username.value, onValueChange = {
                username.value = it
            }, placeholder = { Text(text = "Username") })
            Spacer(modifier = Modifier.height(16.dp))
            TextField(value = password.value, onValueChange = {
                password.value = it
            }, placeholder = { Text(text = "Password") })
            Button(onClick = {
                login(username.value, password.value)
            }, content = { Text(text = "Register") })
        }
        Column(modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally){
            Text(text = "Already have an account?")
            Text("Login", color = Color.Blue, modifier = Modifier.clickable {
                changeScreen()
            })
        }
    }
}