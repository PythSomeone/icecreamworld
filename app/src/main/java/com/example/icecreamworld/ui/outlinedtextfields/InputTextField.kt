package com.example.icecreamworld.ui.outlinedtextfields

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.icecreamworld.ui.theme.ButtonBrown
import com.example.icecreamworld.ui.theme.OutlineBrown

@ExperimentalFoundationApi
@Composable

fun InputTextField(
    label : String
) {
    var data by remember { mutableStateOf("")}
    OutlinedTextField(
        value = data,
        colors = TextFieldDefaults.outlinedTextFieldColors(
            focusedBorderColor = OutlineBrown,
            unfocusedBorderColor = OutlineBrown,
        ),
        shape = RoundedCornerShape(15.dp),
        label = { Text(text = label, fontSize = 20.sp, color = ButtonBrown, fontWeight = FontWeight.Bold) },
        onValueChange = {
            data = it
        },
        modifier = Modifier
            .width(300.dp)
    )
}

@ExperimentalFoundationApi
@Composable
@Preview
fun InputTextFieldPreview(){
    InputTextField(label = "text")
}