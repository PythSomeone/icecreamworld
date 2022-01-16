package com.example.icecreamworld

import android.location.Geocoder
import android.location.Location
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.icecreamworld.ui.appbar.TopAppBar
import com.example.icecreamworld.ui.components.GoogleMaps
import com.example.icecreamworld.ui.components.SearchSection
import com.example.icecreamworld.ui.theme.BackgroundColor
import com.example.icecreamworld.ui.theme.CanvasBrown
import com.google.android.gms.tasks.Task

@Composable
fun MapScreen(
    openDrawer: () -> Unit,
    navController: NavHostController,
    location: Task<Location>,
    shopId: String?
) {
    var value = remember { mutableStateOf(TextFieldValue("")) }
    val view = LocalView.current
    val text = "ICS Maps"
    val currentLocation = location.result
    val geocoder = Geocoder(LocalContext.current)
    val currentLng: Double
    val currentLat: Double
    if (currentLocation != null) {
        currentLng = currentLocation.longitude
        currentLat = currentLocation.latitude
    } else {
        currentLat = geocoder.getFromLocationName("Legnica", 1)[0].latitude
        currentLng = geocoder.getFromLocationName("Legnica", 1)[0].longitude
    }

    Box(
        Modifier
            .fillMaxSize()
            .background(BackgroundColor)
    ) {
        Column(Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {
            TopAppBar(
                backgroundColor = BackgroundColor,
                onButtonClicked = { openDrawer() },
                title = "Maps",
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(20.dp))
            Text(
                text,
                color = CanvasBrown,
                fontSize = 21.sp,
                fontWeight = FontWeight.Bold
            )
            SearchSection(
                textValue = value,
                label = "",
                onDoneActionClick =
                {
                    view.clearFocus()
                },
                onValueChanged = {},
                onClearClick = {
                    value = value
                    view.clearFocus()
                },
                navController = navController
            )
            Spacer(modifier = Modifier.height(20.dp))
            Column(modifier = Modifier.fillMaxHeight()) {
                Box(modifier = Modifier.weight(1f)) {
                    GoogleMaps(currentLat, currentLng, value, shopId)
                }
//                LazyColumn(
//                    modifier = Modifier.weight(1f),
//                    contentPadding = PaddingValues(16.dp)
//                ) {
//                    items(shopList) { shop ->
//                        ShopsCard(navController, shop.name!!, shop.description!!, shop.image!!)
//                    }
//                }
            }

        }
    }
}