package com.example.icecreamworld

import android.location.Geocoder
import android.location.Location
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
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
import com.example.icecreamworld.data.repository.ShopRepository
import com.example.icecreamworld.model.Shop
import com.example.icecreamworld.ui.appbar.TopAppBar
import com.example.icecreamworld.ui.components.SearchSection
import com.example.icecreamworld.ui.components.ShopsCard
import com.example.icecreamworld.ui.theme.BackgroundColor
import com.example.icecreamworld.ui.theme.CanvasBrown
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.tasks.Task
import com.google.firebase.database.ktx.getValue
import com.google.maps.android.SphericalUtil


@Composable
fun ProposedScreen(
    openDrawer: () -> Unit,
    navController: NavHostController,
    location: Task<Location>
) {
    var value = remember { mutableStateOf(TextFieldValue("")) }
    val view = LocalView.current
    val text = "The nearest ice cream shop"
    val shops = ShopRepository
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
    var specifiedLat: Double
    var specifiedLng: Double



    Box(
        Modifier
            .fillMaxSize()
            .background(BackgroundColor)
    ) {
        Column(Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {
            TopAppBar(
                backgroundColor = BackgroundColor,
                onButtonClicked = { openDrawer() },
                title = "Proposed",
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
            LazyColumn(
                modifier = Modifier.fillMaxWidth(),
                contentPadding = PaddingValues(16.dp)
            ) {

                items(shops.data.value.asReversed().distinct()) { snapshot ->
                    val address = geocoder.getFromLocationName(
                        ShopRepository.getShop(snapshot.key!!)?.location,
                        1
                    )
                    if (address.isNotEmpty()) {
                        specifiedLat = address[0].latitude
                        specifiedLng = address[0].longitude
                        val currentLatLng = LatLng(currentLat, currentLng)
                        val specifiedLatLng = LatLng(specifiedLat, specifiedLng)
                        val distance =
                            SphericalUtil.computeDistanceBetween(currentLatLng, specifiedLatLng)
                        if (distance < 10000) {
                            ShopsCard(
                                navController,
                                snapshot.getValue<Shop>()?.name!!,
                                snapshot.getValue<Shop>()?.description!!,
                                snapshot.getValue<Shop>()?.image!!,
                                snapshot.key!!
                            )
                        }
                    }
                }
            }
        }
    }
}