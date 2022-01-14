package com.example.icecreamworld.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
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
import androidx.navigation.NavHostController
import com.example.icecreamworld.data.repository.ShopRepository
import com.example.icecreamworld.model.Shop
import java.util.*
import kotlin.collections.ArrayList


@Composable
fun SearchByTagsSection(
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
        modifier = Modifier.fillMaxWidth()
            .padding(start = 40.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Start
    ) {
        OutlinedTextField(
            modifier = modifier
                .fillMaxWidth(.65f)
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
    }
    if (state) {
        ShopListInSearchByTag(state = textValue)
    }
}


@Composable
fun ShopListInSearchByTag(state: MutableState<TextFieldValue>) {
    val shopList = ArrayList<Shop>()
    ShopRepository.data.value.forEach { item->
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
                shop.menu.forEach {  product ->
                    product.tags.forEach { tag ->
                        if(
                            tag.name!!.lowercase(Locale.getDefault())
                                .contains(state.value.text.lowercase(Locale.getDefault()))
                            || product.name!!.lowercase(Locale.getDefault())
                                .contains(state.value.text.lowercase(Locale.getDefault()))
                        ) resultList.add(shop)

                    }
                }
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
        items(filteredShops) { filteredShop ->
            ShopListItem(
                nameText = filteredShop.name!!,
                onItemClick = {

                },
                image = filteredShop.image
            )
        }
    }
}