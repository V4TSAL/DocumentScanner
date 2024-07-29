package com.example.documentscanner.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextDecoration
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
    val username = remember { mutableStateOf("") }
    val password = remember { mutableStateOf("") }
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceAround
    ) {
        StyledText(text = "Scanner")
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            CustomTextField(value = username.value, placeHolder = "Username") {
                username.value = it
            }
            Spacer(modifier = Modifier.height(16.dp))
            CustomTextField(value = password.value,  placeHolder = "Password") {
                password.value = it
            }
            Spacer(modifier = Modifier.height(48.dp))
            Button(onClick = {
                login(username.value, password.value)
            }, content = { Text(text = "Login", color = Color.Black) }, modifier = Modifier
                .width(175.dp)
                .height(50.dp),shape = RoundedCornerShape(8.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.White
            ))
        }
        Column(modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally){
            Text(text = "Don't have an account?")
            Text("Register", color = Color.Cyan,modifier = Modifier.clickable {
              changeScreen()
            })
        }
    }
}
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SignupScreen(login: (userName: String, password: String) -> Unit,changeScreen: () -> Unit) {
    val username = remember { mutableStateOf("") }
    val password = remember { mutableStateOf("") }
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceAround
    ) {
        StyledText(text = "Scanner")
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            CustomTextField(value = username.value, placeHolder = "Username") {
                username.value = it
            }
            Spacer(modifier = Modifier.height(16.dp))
            CustomTextField(value = password.value, placeHolder = "Password") {
                password.value = it
            }
            Spacer(modifier = Modifier.height(48.dp))
            Button(onClick = {
                login(username.value, password.value)
            }, content = { Text(text = "Register", color = Color.Black) }, modifier = Modifier
                .width(175.dp)
                .height(50.dp),shape = RoundedCornerShape(8.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.White
                ))
        }
        Column(modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally){
            Text(text = "Already have an account?")
            Text("Login", color = Color.Cyan, modifier = Modifier.clickable {
                changeScreen()
            })
        }
    }
}