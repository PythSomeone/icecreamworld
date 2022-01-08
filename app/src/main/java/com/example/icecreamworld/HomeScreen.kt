package com.example.icecreamworld

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.icecreamworld.ui.button.HomeScreenButton
import com.example.icecreamworld.ui.canva.IceCreamWorldCanvas
import com.example.icecreamworld.ui.theme.BackgroundColor
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

@Composable
fun HomeScreen(navController: NavHostController) {

    HomeScreenBackground()

    Box(
        modifier = Modifier
            .background(color = BackgroundColor)
            .fillMaxSize()
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.SpaceEvenly
        ) {
            IceCreamWorldCanvas()
            Column(
                Modifier
                    .fillMaxSize()
                    .padding(horizontal = 52.dp),
                verticalArrangement = Arrangement.Bottom
            ) {
                HomeScreenButton(
                    text = "Guest",
                    navController = navController,
                    route = "ProposedPage"
                )
                HomeScreenButton(
                    text = "Add your ICS",
                    navController = navController,
                    route = "AddShopScreen"
                )
                if (Firebase.auth.currentUser != null) {
                    HomeScreenButton(
                        text = "Admin",
                        navController = navController,
                        route = "ManagementPage"
                    )
                } else {
                    HomeScreenButton(
                        text = "Admin",
                        navController = navController,
                        route = "LoginPage"
                    )
                }

            }
        }
    }
}

@Composable
fun HomeScreenBackground() {
    Image(
        painterResource(id = R.mipmap.ic_ice_cream_foreground),
        contentDescription = null,
        contentScale = ContentScale.Crop,
        modifier = Modifier.fillMaxHeight(),
    )
}
