package com.example.icecreamworld

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.example.icecreamworld.ui.components.ShopFormSection

@ExperimentalMaterialApi
@ExperimentalFoundationApi
@Composable
fun ShopFormScreen(
    navController: NavController,
    shopId: String? = null,
) {
    Column(modifier = Modifier.fillMaxSize()) {
        ShopFormSection(shopId = shopId, navController = navController)
    }
}