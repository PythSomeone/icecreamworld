package com.example.icecreamworld.ui.components

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.icecreamworld.model.Shop
import com.example.icecreamworld.ui.theme.CanvasBrown

@ExperimentalFoundationApi
@Composable

fun ShopText(
    label: String,
    text: String
) {
    Column (
    modifier = Modifier
        .padding(horizontal = 20.dp)
        .fillMaxWidth(),
    horizontalAlignment = Alignment.CenterHorizontally,
    content = {
        Text(
            label,
            modifier = Modifier
                .padding()
                .fillMaxWidth(),
            color = CanvasBrown,
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold
        )
        Card(
            modifier = Modifier
                .padding()
                .fillMaxWidth()
                .wrapContentHeight(),
            shape = MaterialTheme.shapes.medium,
            elevation = 15.dp,
            backgroundColor = MaterialTheme.colors.surface
        ) {

            Text(
                modifier = Modifier
                    .padding(10.dp),
                text = text!!,
                fontSize = 16.sp,
            )

        }
    }


    )
}