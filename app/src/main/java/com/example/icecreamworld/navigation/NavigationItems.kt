package com.example.icecreamworld.navigation

import android.location.Location
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.material.DrawerValue
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ModalDrawer
import androidx.compose.material.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.icecreamworld.*
import com.example.icecreamworld.ui.components.*
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.launch

@ExperimentalMaterialApi
@ExperimentalFoundationApi
@Composable
fun NavigationPage(location: Task<Location>) {
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
    ) {
        NavHost(
            navController = navController,
            startDestination = "HomePage"
        ) {
            composable("HomePage") {
                HomeScreen(navController)
            }
            composable(DrawerScreens.Proposed.route) {
                ProposedScreen(openDrawer = { openDrawer() }, navController)
            }
            composable(DrawerScreens.Search.route) {

            }
            composable(DrawerScreens.Map.route) {
                MapScreen(openDrawer = { openDrawer() }, navController, location)
            }
            composable(DrawerScreens.ManagementPanel.route) {
                ManagementScreen(navController = navController)
            }
            composable("ManageForm/{FormId}"){ backstackEntry ->
                ManageFormsPage(
                    navController,
                    backstackEntry.arguments?.getString("FormId"),
                )
            }
            composable(DrawerScreens.NewIceCreamShop.route) { backstackEntry ->
                ShopFormScreen(
                    navController,
                    backstackEntry.arguments?.getString("ShopId"),
                )
            }
            composable("ShopForm/{shopId}") { backstackEntry ->
                ShopFormScreen(
                    navController,
                    backstackEntry.arguments?.getString("shopId"),
                )
            }
            composable(DrawerScreens.Logout.route) {
                Firebase.auth.signOut()
                HomeScreen(navController)
            }
            composable("Shop/{ShopId}") { backstackEntry ->
                ShopScreen(
                    navController,
                    backstackEntry.arguments?.getString("ShopId"),
                )
            }
            composable("LoginPage") {
                LoginScreen(navController)
            }
            composable("EditListScreen") {
                EditListScreen(navController = navController)
            }
            composable("ApproveListScreen") {
                ApproveListScreen(navController = navController)
            }
        }
    }
}