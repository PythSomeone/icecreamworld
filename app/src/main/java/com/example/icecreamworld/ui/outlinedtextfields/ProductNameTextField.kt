package com.example.icecreamworld.ui.outlinedtextfields

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.icecreamworld.model.Product
import com.example.icecreamworld.ui.theme.ButtonBrown

@ExperimentalFoundationApi
@Composable

fun ProductNameTextField(
    label: String,
    product: Product
) {
    OutlinedTextField(
        value = product.name!!,
        shape = RoundedCornerShape(15.dp),
        label = {
            Text(
                text = label,
                fontSize = 10.sp,
                color = ButtonBrown,
                fontWeight = FontWeight.Bold
            )
        },
        onValueChange = {
            product.name = it
        },
        readOnly = true
    )
}