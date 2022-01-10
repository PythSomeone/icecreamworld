package com.example.icecreamworld.ui.appbar

import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.icecreamworld.ui.theme.BackgroundColor

@Composable
fun TopAppBar(
    backgroundColor: androidx.compose.ui.graphics.Color,
    modifier: Modifier = Modifier,
    onButtonClicked: () -> Unit,
    title: String
) {
    androidx.compose.material.TopAppBar(
        title = { Text(text = title) },
        navigationIcon = {
            IconButton(onClick = { onButtonClicked() }) {
                Icon(Icons.Filled.Menu, contentDescription = "MenuIcon")
            }
        },
        backgroundColor = BackgroundColor,
        elevation = 0.dp
    )
}