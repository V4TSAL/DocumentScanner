package com.example.documentscanner.screens

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun StyledText(text: String,modifier : Modifier = Modifier) {
    val redPart = "Document"

    val annotatedString = AnnotatedString.Builder().apply {
        // Style the "Rejection Reason:" text as red
        withStyle(style = SpanStyle(color = Color.White, fontSize = 32.sp,fontWeight = FontWeight(700))) {
            append(redPart)
        }
        // Style the remaining text as black
        withStyle(style = SpanStyle(color = Color.Cyan, fontSize = 32.sp, fontWeight = FontWeight(700))) {
            append(text)
        }
    }.toAnnotatedString()

    Text(text = annotatedString,modifier = modifier)
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomTextField(value:String,placeHolder:String,onValueChange:(String)->Unit) {
    TextField(value = value, onValueChange = {
        onValueChange(it)
    }, placeholder = { Text(text = placeHolder,color = Color.Black) }, modifier = Modifier.fillMaxWidth(),shape = RoundedCornerShape(16.dp),
        colors = TextFieldDefaults.textFieldColors(
            textColor = Color.Black,
            disabledTextColor = Color.Transparent,
            containerColor = Color.White,
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
            disabledIndicatorColor = Color.Transparent
        )
    )
}
