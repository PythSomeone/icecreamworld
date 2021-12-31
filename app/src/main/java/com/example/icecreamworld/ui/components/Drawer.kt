package com.example.icecreamworld.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment.Companion.Center
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.icecreamworld.ui.theme.BackgroundColor
import com.example.icecreamworld.ui.theme.CanvasBrown
import com.example.icecreamworld.ui.theme.DrawerBackgroundColor

sealed class DrawerScreens(val title: String, val route: String){
    object Proposed : DrawerScreens("Proposed", "ProposedPage")
    object Search : DrawerScreens("Search", "SearchPage")
    object Map : DrawerScreens( "Map", "MapPage")
    object ManagementPanel : DrawerScreens( "Management Panel", "ManagementPage")
    object NewIceCreamShop : DrawerScreens( "New Ice Cream Shop", "NewIceCreamShopPage")
    object NewMenu : DrawerScreens( "New Menu", "AddMenuScreen")
    object Logout: DrawerScreens("Log out", "LogOut")
}

private val screensForAdmin = listOf(
    DrawerScreens.Proposed,
    DrawerScreens.Search,
    DrawerScreens.Map,
    DrawerScreens.ManagementPanel,
    DrawerScreens.NewIceCreamShop,
    DrawerScreens.NewMenu,
    DrawerScreens.Logout
)

@Composable
fun Drawer(
    modifier: Modifier = Modifier,
    onDestinationClicked: (route: String) -> Unit
) {
    Column(
        modifier
            .fillMaxSize()
            .background(color = DrawerBackgroundColor),
        horizontalAlignment = CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {
        Spacer(modifier = Modifier.height(50.dp))
        Box(modifier = Modifier
            .fillMaxWidth()
            .height(50.dp)
        ){
            Text(
                text = "Menu",
                style = MaterialTheme.typography.h4,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .align(Center),
                color = CanvasBrown
            )
        }
        screensForAdmin.forEach { screen ->
            Spacer(Modifier.height(24.dp))
            Box(modifier = Modifier
                .fillMaxWidth()
                .height(50.dp)
            ){
                Text(
                    text = screen.title,
                    style = MaterialTheme.typography.h6,
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .align(Center)
                        .clickable { onDestinationClicked(screen.route) },
                    color = CanvasBrown
                )
                Divider(thickness = 1.dp,
                modifier = Modifier.padding(horizontal = 50.dp),
                color = CanvasBrown)
            }
        }
    }
}