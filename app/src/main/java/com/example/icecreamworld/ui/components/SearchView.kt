package com.example.icecreamworld.ui.components

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
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusState
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import coil.compose.rememberImagePainter
import com.example.icecreamworld.data.repository.ShopRepository
import com.example.icecreamworld.model.Shop
import com.example.icecreamworld.ui.imageview.RoundImage
import com.example.icecreamworld.ui.theme.BackgroundColor
import com.example.icecreamworld.ui.theme.CanvasBrown
import java.util.*
import kotlin.collections.ArrayList

@Composable
fun SearchSection(
    modifier: Modifier = Modifier,
    textValue: MutableState<TextFieldValue>,
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
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        OutlinedTextField(
            modifier = modifier
                .fillMaxWidth(.6f)
                .onFocusChanged { onFocusChanged(it) }
                .align(Alignment.CenterVertically),
            value = textValue.value,
            onValueChange = { value ->
                textValue.value = value
                state = true
            },
            label = { Text(text = label, textAlign = TextAlign.Center) },
            textStyle = MaterialTheme.typography.subtitle1,
            singleLine = true,
            trailingIcon = {
                IconButton(onClick = {
                    onClearClick()
                    state = false
                    textValue.value = TextFieldValue("")
                }) {
                    Icon(imageVector = Icons.Filled.Clear, contentDescription = "Clear")
                }
            },
            keyboardActions = KeyboardActions(onDone = { onDoneActionClick() }),
            keyboardOptions = KeyboardOptions(
                imeAction = ImeAction.Done,
                keyboardType = KeyboardType.Text
            ),
            shape = RoundedCornerShape(100f)
        )
        FloatingActionButton(
            onClick = { navController.navigate("MapPage") },
            backgroundColor = CanvasBrown,
            shape = RoundedCornerShape(15.dp)
        ) {
            Icon(imageVector = Icons.Outlined.LocationOn, contentDescription = "Map")
        }
    }
    if (state) {
        ShopListInSearch(state = textValue, navController)
    }
}

@Composable
fun ShopListItem(nameText: String, image: String?, onItemClick: () -> Unit) {
    Row(
        modifier = Modifier
            .clickable(onClick = { onItemClick() })
            .background(BackgroundColor)
            .height(70.dp)
            .fillMaxWidth()
            .padding(PaddingValues(8.dp, 16.dp))
    ) {
        RoundImage(image = rememberImagePainter(image))
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.padding(start = 30.dp)
        ) {
            Text(
                text = nameText,
                fontSize = 18.sp,
                color = CanvasBrown,
                textAlign = TextAlign.Center
            )
        }
    }
    Divider(Modifier.height(1.dp), color = CanvasBrown)
}

@Composable
fun ShopListInSearch(
    state: MutableState<TextFieldValue>,
    navController: NavHostController
) {
    val shopList = ArrayList<Shop>()
    ShopRepository.data.value.forEach { item ->
        shopList.add(ShopRepository.getShop(item.key!!)!!)
    }
    var filteredShops: ArrayList<Shop>
    LazyColumn(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 50.dp)
    ) {
        if (state.value.text.isEmpty()) {
            filteredShops = shopList
        } else {
            val resultList = ArrayList<Shop>()
            for (shop in shopList) {
                if (shop.name!!.lowercase(Locale.getDefault())
                        .contains(state.value.text.lowercase(Locale.getDefault()))
                    || shop.description!!.lowercase(Locale.getDefault())
                        .contains(
                            state.value.text.lowercase(Locale.getDefault())
                        )
                ) {
                    resultList.add(shop)
                }
            }
            filteredShops = resultList
        }
        items(filteredShops.distinct().asReversed()) { filteredShop ->
            var key = ""
            ShopRepository.data.value.forEach { item ->
                if (ShopRepository.getShop(item.key!!)!! == filteredShop) {
                    key = item.key!!
                }
            }
            ShopListItem(
                nameText = filteredShop.name!!,
                filteredShop.image,
                onItemClick = {
                    navController.navigate("Shop/${key}")
                }
            )
        }
    }
}