package com.example.icecreamworld

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.icecreamworld.ui.components.ShopFormSection
import kotlinx.coroutines.delay

@ExperimentalFoundationApi
@Composable
fun ShopFormScreen(
    navController: NavController,
    shopId: String? = null,
) {
    var refreshing by remember { mutableStateOf(false) }

    Column(modifier = Modifier.fillMaxSize()) {
        Spacer(modifier = Modifier.height(4.dp))
        ShopFormSection(shopId, navController)
        Spacer(modifier = Modifier.height(25.dp))
        //refresh
        LaunchedEffect(refreshing) {
            if (refreshing) {
                delay(2000)
                refreshing = false
            }
        }
    }
}

//@ExperimentalFoundationApi
//@Composable
//@Preview
//fun EditShopPreview() {
//    EditShopScreen(rememberNavController())
//}

