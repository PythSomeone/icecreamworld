package com.example.icecreamworld

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.outlined.LocationOn
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusState
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.icecreamworld.model.Shop
import com.example.icecreamworld.ui.appbar.TopAppBar
import com.example.icecreamworld.ui.theme.BackgroundColor
import com.example.icecreamworld.ui.theme.CanvasBrown
import java.util.*
import kotlin.collections.ArrayList


@Composable
fun ProposedScreen(openDrawer: () -> Unit, navController: NavHostController) {
    var value by remember { mutableStateOf("") }
    val view = LocalView.current
    val text = "The nearest ice cream shop"

    val shopList = listOf(
        Shop("gjo","dsafas","https://www.google.com/url?sa=i&url=https%3A%2F%2Funsplash.com%2Fimages%2Ffood%2Fice-cream&psig=AOvVaw0kfgGV00R3I20tf7BTZjKX&ust=1641045126027000&source=images&cd=vfe&ved=0CAsQjRxqFwoTCMDV_M-XjvUCFQAAAAAdAAAAABAD"),
        Shop("abc","def","https://www.google.com/url?sa=i&url=https%3A%2F%2Funsplash.com%2Fimages%2Ffood%2Fice-cream&psig=AOvVaw0kfgGV00R3I20tf7BTZjKX&ust=1641045126027000&source=images&cd=vfe&ved=0CAsQjRxqFwoTCMDV_M-XjvUCFQAAAAAdAAAAABAD")
    )

    Box(
        Modifier
            .fillMaxSize()
            .background(BackgroundColor)) {
        Column(Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {
            TopAppBar(
                backgroundColor = BackgroundColor,
                onButtonClicked = { openDrawer() },
                title = "Proposed",
                modifier = Modifier.fillMaxWidth())
            Spacer(modifier = Modifier.height(20.dp))
            Text(
                text,
                color = CanvasBrown,
                fontSize = 21.sp,
                fontWeight = FontWeight.Bold
            )
            SearchSection(
                value = value,
                label = "",
                onDoneActionClick =
                {
                    view.clearFocus()
                },
                onValueChanged = {},
                onClearClick = {
                    value = ""
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
                    ShopsCard(shop.name!!, shop.description!!, shop.image!!)
                }
            }
        }
    }

}



@Composable
fun SearchSection(
    modifier: Modifier = Modifier,
    value: String,
    label: String,
    onDoneActionClick: () -> Unit = {},
    onClearClick: () -> Unit = {},
    onFocusChanged: (FocusState) -> Unit = {},
    onValueChanged: (String) -> Unit,
    navController: NavHostController
) {
    var state by remember {
        mutableStateOf(false)
    }

    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = CenterVertically,
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        var value by remember {
            mutableStateOf(value)
        }
        OutlinedTextField(
            modifier = modifier
                .fillMaxWidth(.6f)
                .onFocusChanged { onFocusChanged(it) }
                .align(CenterVertically),
            value = value,
            onValueChange = {
                value = it
                state = true
            },
            label = { Text(text = label) },
            textStyle = MaterialTheme.typography.subtitle1,
            singleLine = true,
            trailingIcon = {
                IconButton(onClick = {
                    onClearClick()
                    state = false
                    value = ""
                }) {
                    Icon(imageVector = Icons.Filled.Clear, contentDescription = "Clear")
                }
            },
            keyboardActions = KeyboardActions(onDone = { onDoneActionClick() }),
            keyboardOptions = KeyboardOptions(
                imeAction = ImeAction.Done,
                keyboardType = KeyboardType.Text
            ),
            shape = RoundedCornerShape(100f),
        )
        FloatingActionButton(onClick = { navController.navigate("MapPage") },backgroundColor = CanvasBrown, shape = RoundedCornerShape(15.dp)) {
            Icon(imageVector = Icons.Outlined.LocationOn, contentDescription = "Map")
        }
    }
    if (state)
    {
        ShopListInSearch(state = value)
    }
}

@Composable
fun ShopListItem(nameText: String, onItemClick: (String) -> Unit) {
    Row(
        modifier = Modifier
            .clickable(onClick = { /*TODO*/ })
            .background(BackgroundColor)
            .height(57.dp)
            .fillMaxWidth()
            .padding(PaddingValues(8.dp, 16.dp))
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(text = nameText, fontSize = 18.sp, color = CanvasBrown, textAlign = TextAlign.Center)
            Divider(color = CanvasBrown)
        }

    }
}

@Composable
fun ShopListInSearch(state: String){
    val shopList = arrayListOf(
        Shop("gjo","dsafas","https://www.google.com/url?sa=i&url=https%3A%2F%2Funsplash.com%2Fimages%2Ffood%2Fice-cream&psig=AOvVaw0kfgGV00R3I20tf7BTZjKX&ust=1641045126027000&source=images&cd=vfe&ved=0CAsQjRxqFwoTCMDV_M-XjvUCFQAAAAAdAAAAABAD"),
        Shop("abc","def","https://www.google.com/url?sa=i&url=https%3A%2F%2Funsplash.com%2Fimages%2Ffood%2Fice-cream&psig=AOvVaw0kfgGV00R3I20tf7BTZjKX&ust=1641045126027000&source=images&cd=vfe&ved=0CAsQjRxqFwoTCMDV_M-XjvUCFQAAAAAdAAAAABAD")
    )
    var filteredShops: ArrayList<Shop>
    LazyColumn(modifier = Modifier
        .fillMaxWidth()
        .padding(horizontal = 50.dp)) {
        filteredShops = if (state.isEmpty()) {
            shopList
        } else {
            val resultList = ArrayList<Shop>()
            for (shop in shopList) {
                if (shop.name!!.lowercase(Locale.getDefault())
                        .contains(state.lowercase(Locale.getDefault()))
                    || shop.description!!.lowercase(Locale.getDefault())
                        .contains(state.lowercase(Locale.getDefault()))
                ) {
                    resultList.add(shop)
                }
            }
            resultList
        }
        items(filteredShops) { filteredShop ->
            ShopListItem(
                nameText = filteredShop.name!!,
                onItemClick = { selectedShop ->
                    /* Add code later */
                }
            )
        }
    }
}

@Composable
fun ShopsCard(name: String, description: String, image: String) {
    Card(
        modifier = Modifier
            .padding(10.dp)
            .fillMaxWidth()
            .wrapContentHeight(),
        shape = MaterialTheme.shapes.medium,
        elevation = 15.dp,
        backgroundColor = MaterialTheme.colors.surface
    ) {
        Row(
            verticalAlignment = CenterVertically
        ) {
            Image(
                painter = painterResource(R.drawable.ic_launcher_foreground),
                contentDescription = null,
                modifier = Modifier
                    .size(130.dp)
                    .padding(8.dp),
                contentScale = ContentScale.Fit,
            )
            Column(
                Modifier
                    .padding(8.dp)
                    .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally) {
                Text(
                    text = name,
                    style = MaterialTheme.typography.h4,
                    color = CanvasBrown,
                )
                Text(
                    text = description,
                    style = MaterialTheme.typography.body2,
                    color = CanvasBrown
                )
                Spacer(modifier = Modifier.height(10.dp))
                FloatingActionButton(onClick = { /*TODO*/ }, backgroundColor = CanvasBrown, contentColor = Color.White, modifier = Modifier
                    .height(30.dp)
                    .width(150.dp)) {
                    Text("See More")
                }
            }
        }
    }
}
