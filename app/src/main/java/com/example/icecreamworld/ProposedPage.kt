package com.example.icecreamworld

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.icecreamworld.model.Shop
import com.example.icecreamworld.ui.appbar.TopAppBar
import com.example.icecreamworld.ui.components.SearchSection
import com.example.icecreamworld.ui.components.ShopsCard
import com.example.icecreamworld.ui.theme.BackgroundColor
import com.example.icecreamworld.ui.theme.CanvasBrown


@Composable
fun ProposedScreen(openDrawer: () -> Unit, navController: NavHostController) {
    var value = remember { mutableStateOf(TextFieldValue("")) }
    val view = LocalView.current
    val text = "The nearest ice cream shop"

    val shopList = listOf(
        Shop(
            "gjo",
            "dsafas",
            "https://www.google.com/url?sa=i&url=https%3A%2F%2Funsplash.com%2Fimages%2Ffood%2Fice-cream&psig=AOvVaw0kfgGV00R3I20tf7BTZjKX&ust=1641045126027000&source=images&cd=vfe&ved=0CAsQjRxqFwoTCMDV_M-XjvUCFQAAAAAdAAAAABAD"
        ),
        Shop(
            "abc",
            "def",
            "https://www.google.com/url?sa=i&url=https%3A%2F%2Funsplash.com%2Fimages%2Ffood%2Fice-cream&psig=AOvVaw0kfgGV00R3I20tf7BTZjKX&ust=1641045126027000&source=images&cd=vfe&ved=0CAsQjRxqFwoTCMDV_M-XjvUCFQAAAAAdAAAAABAD"
        )
    )

    Box(
        Modifier
            .fillMaxSize()
            .background(BackgroundColor)
    ) {
        Column(Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {
            TopAppBar(
                backgroundColor = BackgroundColor,
                onButtonClicked = { openDrawer() },
                title = "Proposed",
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(20.dp))
            Text(
                text,
                color = CanvasBrown,
                fontSize = 21.sp,
                fontWeight = FontWeight.Bold
            )
            SearchSection(
                textValue = value,
                label = "",
                onDoneActionClick =
                {
                    view.clearFocus()
                },
                onValueChanged = {},
                onClearClick = {
                    value = value
                    view.clearFocus()
                },
                navController = navController
            )
            Spacer(modifier = Modifier.height(20.dp))
            LazyColumn(
                modifier = Modifier.fillMaxWidth(),
                contentPadding = PaddingValues(16.dp)
            ) {
                items(shopList) { shop ->
                    ShopsCard(navController, shop.name!!, shop.description!!, shop.image!!)
                }
            }
        }
    }
}