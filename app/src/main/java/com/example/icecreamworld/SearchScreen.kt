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
import com.example.icecreamworld.data.repository.ShopRepository
import com.example.icecreamworld.model.Shop
import com.example.icecreamworld.ui.appbar.TopAppBar
import com.example.icecreamworld.ui.components.SearchByTagsSection
import com.example.icecreamworld.ui.components.SearchSection
import com.example.icecreamworld.ui.components.ShopsCard
import com.example.icecreamworld.ui.theme.BackgroundColor
import com.example.icecreamworld.ui.theme.CanvasBrown
import com.google.firebase.database.ktx.getValue

@Composable
fun SearchScreen(openDrawer: () -> Unit, navController: NavHostController) {
    var value = remember { mutableStateOf(TextFieldValue("")) }
    var valueByTag = remember { mutableStateOf(TextFieldValue("")) }
    val view = LocalView.current
    val text = "Search"
    val shops = ShopRepository


    Box(
        Modifier
            .fillMaxSize()
            .background(BackgroundColor)
    ) {
        Column(Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {
            TopAppBar(
                backgroundColor = BackgroundColor,
                onButtonClicked = { openDrawer() },
                title = text,
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
                label = "Shop Name - Shop Description",
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
            Spacer(modifier = Modifier.height(50.dp))
            SearchByTagsSection(
                textValue = valueByTag,
                label = "Product Name - Tag",
                onDoneActionClick = { view.clearFocus() },
                onValueChanged = {},
                navController = navController,
                onClearClick = {
                    valueByTag = valueByTag
                    view.clearFocus()
                })

            LazyColumn(
                modifier = Modifier.fillMaxWidth(),
                contentPadding = PaddingValues(16.dp)
            ) {
                items(shops.data.value.asReversed().distinct()) { snapshot ->
                    ShopsCard(
                        navController,
                        snapshot.getValue<Shop>()?.name!!,
                        snapshot.getValue<Shop>()?.description!!,
                        snapshot.getValue<Shop>()?.image!!,
                        snapshot.key!!
                    )
                }
            }
        }
    }
}