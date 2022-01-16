package com.example.icecreamworld.ui.button

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material.ExtendedFloatingActionButton
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.icecreamworld.ui.theme.CanvasBrown

@Composable
fun HomeScreenButton(text: String, navController: NavHostController, route: String) {
    ExtendedFloatingActionButton(
        text = { Text(text, fontSize = 20.sp, fontFamily = FontFamily.Serif) },
        onClick = { navController.navigate(route) },
        backgroundColor = CanvasBrown.copy(0.9f),
        contentColor = Color.White,
        modifier = Modifier.fillMaxWidth()
    )
    Spacer(modifier = Modifier.height(60.dp))
}