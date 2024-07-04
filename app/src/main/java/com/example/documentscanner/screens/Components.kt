package com.example.documentscanner.screens

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.sp

@Composable
fun StyledText(text: String,modifier : Modifier = Modifier) {
    val redPart = "Document"

    val annotatedString = AnnotatedString.Builder().apply {
        // Style the "Rejection Reason:" text as red
        withStyle(style = SpanStyle(color = Color.Black, fontSize = 32.sp,fontWeight = FontWeight(700))) {
            append(redPart)
        }
        // Style the remaining text as black
        withStyle(style = SpanStyle(color = Color.Blue, fontSize = 32.sp, fontWeight = FontWeight(700))) {
            append(text)
        }
    }.toAnnotatedString()

    Text(text = annotatedString,modifier = modifier)
}

