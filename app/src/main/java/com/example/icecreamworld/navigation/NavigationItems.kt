package com.example.icecreamworld.navigation

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.material.DrawerValue
import androidx.compose.material.ModalDrawer
import androidx.compose.material.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.icecreamworld.AddShopScreen
import com.example.icecreamworld.EditShopScreen
import com.example.icecreamworld.HomeScreen
import com.example.icecreamworld.ProposedScreen
import com.example.icecreamworld.ui.components.Drawer
import com.example.icecreamworld.ui.components.DrawerScreens
import kotlinx.coroutines.launch

@ExperimentalFoundationApi
@Composable
fun NavigationPage() {
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()
    val navController = rememberNavController()
    val openDrawer = {
        scope.launch {
            drawerState.open()
        }
    }
    ModalDrawer(
        drawerState = drawerState,
        gesturesEnabled = drawerState.isOpen,
        drawerContent = {
            Drawer(
                onDestinationClicked = { route ->
                    scope.launch {
                        drawerState.close()
                    }
                    navController.navigate(route) {
                        launchSingleTop = true
                        popUpTo(navController.graph.startDestinationId)
                    }
                }
            )
        }
    ){
        NavHost(
            navController = navController,
            startDestination = "HomePage"
        ) {
            composable("HomePage") {
                HomeScreen(navController)
            }
            composable(DrawerScreens.Proposed.route) {
                //Sample Usage of drawer. Inside function simply use TopAppBar with "onButtonClicked = { openDrawer() }"
                ProposedScreen ( openDrawer = {openDrawer()}, navController)
            }
            composable(DrawerScreens.Search.route) {

            }
            composable(DrawerScreens.Map.route) {

            }
            composable(DrawerScreens.ManagementPanel.route) {

            }
            composable(DrawerScreens.NewIceCreamShop.route) {

            }
            composable(DrawerScreens.NewMenu.route) {

            }
            composable(DrawerScreens.Logout.route) {

            }
            composable("AddShopScreen") {
                AddShopScreen(navController)
            }
            composable("EditShopScreen") {
                EditShopScreen(navController)
            }
        }
    }
}