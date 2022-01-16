package com.example.icecreamworld

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.material.DrawerValue
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.rememberDrawerState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.navigation.compose.rememberNavController
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.icecreamworld.ui.components.ManagementScreen
import com.example.icecreamworld.ui.components.ShopsCard
import com.example.icecreamworld.ui.theme.IceCreamWorldTheme
import kotlinx.coroutines.launch
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class Test {

    @get:Rule
    val composeTestRule = createComposeRule()

    @ExperimentalFoundationApi
    @ExperimentalMaterialApi
    @Test
    fun firstPageTest() {
        composeTestRule.setContent {
            IceCreamWorldTheme {
                HomeScreen(navController = rememberNavController())
            }
        }
        composeTestRule.onNodeWithText("Guest").assertIsDisplayed()
        composeTestRule.onNodeWithText("Add your ICS").assertIsDisplayed()
        composeTestRule.onNodeWithText("Admin").assertIsDisplayed()
    }

    @ExperimentalFoundationApi
    @ExperimentalMaterialApi
    @Test
    fun adminLoginPageTest() {
        composeTestRule.setContent {
            IceCreamWorldTheme {
                LoginScreen(navController = rememberNavController())
            }
        }
        composeTestRule.onNodeWithText("Email").assertIsDisplayed()
        composeTestRule.onNodeWithText("Password").assertIsDisplayed()
        composeTestRule.onNodeWithText("Login").assertIsDisplayed()
        composeTestRule.onNodeWithText("Login").assertIsNotEnabled()
        composeTestRule.onNodeWithText("Email").performTextInput("mail@mail.com")
        composeTestRule.onNodeWithText("Password").performTextInput("Pass")
        composeTestRule.onNodeWithText("Login").assertIsEnabled()
    }

    @ExperimentalFoundationApi
    @ExperimentalMaterialApi
    @Test
    fun addICSPageTest() {
        composeTestRule.setContent {
            IceCreamWorldTheme {
                ShopFormScreen(navController = rememberNavController())
            }
        }
        composeTestRule.onNodeWithText("Choose photo").assertIsDisplayed()
        composeTestRule.onNodeWithText("Change name").assertIsDisplayed()
        composeTestRule.onNodeWithText("Change description").assertIsDisplayed()
        composeTestRule.onNodeWithText("Change location").assertIsDisplayed()
        composeTestRule.onNodeWithText("Change website address").assertIsDisplayed()
        composeTestRule.onNodeWithText("Menu").assertIsDisplayed()
        composeTestRule.onNodeWithText("+").assertIsDisplayed()
        composeTestRule.onNodeWithText("Save").assertIsDisplayed()
        composeTestRule.onNodeWithText("Change name").performTextInput("test")
        composeTestRule.onNodeWithText("Change description").performTextInput("test")
        composeTestRule.onNodeWithText("Change location").performTextInput("test")
        composeTestRule.onNodeWithText("Change website address").performTextInput("test")
    }

    @ExperimentalFoundationApi
    @ExperimentalMaterialApi
    @Test
    fun searchPageTest() {
        composeTestRule.setContent {
            val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
            val scope = rememberCoroutineScope()
            val openDrawer = {
                scope.launch {
                    drawerState.open()
                }
            }
            IceCreamWorldTheme {
                SearchScreen(openDrawer = { openDrawer() }, navController = rememberNavController())
            }
        }
        composeTestRule.onNodeWithText("Shop Name - Shop Description").assertIsDisplayed()
        composeTestRule.onNodeWithText("Product Name - Tag").assertIsDisplayed()
        composeTestRule.onNodeWithText("Shop Name - Shop Description").performTextInput("test")
        composeTestRule.onNodeWithText("Product Name - Tag").performTextInput("test")
    }

    @ExperimentalFoundationApi
    @ExperimentalMaterialApi
    @Test
    fun managementPageTest() {
        composeTestRule.setContent {
            IceCreamWorldTheme {
                ManagementScreen(navController = rememberNavController())
            }
        }
        composeTestRule.onNodeWithText("Choose what you want to do").assertIsDisplayed()
        composeTestRule.onNodeWithText("Choose what you want to do").performClick()
        composeTestRule.onNodeWithText("Manage ICS forms").assertIsDisplayed()
        composeTestRule.onNodeWithText("Manage ICS").assertIsDisplayed()
    }

    @ExperimentalFoundationApi
    @ExperimentalMaterialApi
    @Test
    fun shopCardTest() {
        composeTestRule.setContent {
            IceCreamWorldTheme {
                ShopsCard(
                    navController = rememberNavController(),
                    name = "test",
                    description = "testDesc",
                    image = "TestImage",
                    key = "1"
                )
            }
        }
        composeTestRule.onNodeWithText("test").assertIsDisplayed()
        composeTestRule.onNodeWithText("testDesc").assertIsDisplayed()
    }
}