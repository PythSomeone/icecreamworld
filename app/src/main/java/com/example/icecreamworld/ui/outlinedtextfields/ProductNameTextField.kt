package com.example.icecreamworld.ui.outlinedtextfields

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.icecreamworld.model.Product
import com.example.icecreamworld.ui.theme.ButtonBrown
import com.example.icecreamworld.ui.theme.OutlineBrown

@ExperimentalFoundationApi
@Composable

fun ProductNameTextField(
    label: String,
    product: Product
) {
    OutlinedTextField(
        value = product.name!!,
        shape = RoundedCornerShape(15.dp),
        label = { Text(text = label, fontSize = 20.sp, color = ButtonBrown, fontWeight = FontWeight.Bold) },
        onValueChange = {
            product.name = it
        },
        modifier = Modifier
            .width(100.dp)
    )
}

//@ExperimentalFoundationApi
//@Composable
//@Preview
//fun InputTextFieldPreview(){
//    InputTextField(label = "text", name = "name")
//}