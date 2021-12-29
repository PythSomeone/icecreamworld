package com.example.icecreamworld

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.material.ExperimentalMaterialApi
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.icecreamworld.AddShopScreen
import com.example.icecreamworld.ui.theme.IceCreamWorldTheme

//import com.example.noinstagram.ui.theme.NoInstagramTheme
//import com.example.noinstagram.utils.database.PostHandler
//import com.example.noinstagram.utils.database.UserHandler
//import com.example.noinstagram.viewmodel.AuthViewModel
//import com.google.firebase.auth.FirebaseAuth


class MainActivity : ComponentActivity() {
//    private val authViewModel: AuthViewModel by viewModels()

    @ExperimentalMaterialApi
    @ExperimentalFoundationApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        UserHandler.userListener()
//        PostHandler.postListener()
        setContent {
            IceCreamWorldTheme {
                val navController = rememberNavController()
                NavHost(
                    navController,
                    "EditShopScreen"
                )
                {
                    composable("AddShopScreen") {
                        AddShopScreen(navController)
                    }
                    composable("EditShopScreen") {
                        EditShopScreen(navController)
                    }
                }
            }
        }
    }
}