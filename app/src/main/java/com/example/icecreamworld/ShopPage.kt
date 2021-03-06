package com.example.icecreamworld

import android.content.Intent
import android.net.Uri
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.material.Card
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import coil.compose.rememberImagePainter
import com.example.icecreamworld.data.repository.ShopRepository
import com.example.icecreamworld.model.Shop
import com.example.icecreamworld.ui.components.ShopText
import com.example.icecreamworld.ui.theme.BackgroundCardColor
import com.example.icecreamworld.ui.theme.BackgroundColor
import com.example.icecreamworld.ui.theme.CanvasBrown
import com.example.icecreamworld.ui.theme.OutlineBrown
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase


@ExperimentalFoundationApi
@Composable
fun ShopScreen(
    navController: NavHostController,
    shopId: String?
) {

    var shop = ShopRepository.getShop(shopId!!)
    if (shop == null) {
        shop = Shop()
    }
    val scrollState = rememberScrollState()
    var url = shop.websiteLink
    if (!shop.websiteLink?.startsWith("http://")!! && !shop.websiteLink?.startsWith("https://")!!)
        url = "http://" + url
    val context = LocalContext.current


    Box(
        Modifier
            .fillMaxSize()
            .background(BackgroundColor)
    ) {
        Column(
            Modifier
                .verticalScroll(state = scrollState)
                .padding(horizontal = 30.dp)
                .fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                content = {
                    Image(
                        painter = rememberImagePainter(shop.image),
                        contentDescription = null,
                        modifier = Modifier
                            .border(
                                width = 1.dp,
                                color = Color.LightGray,
                            )
                            .padding(3.dp)
                            .height(200.dp)
                    )
                }
            )
            Spacer(modifier = Modifier.height(20.dp))
            Text(
                shop.name!!,
                color = CanvasBrown,
                fontSize = 30.sp,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(20.dp))

            ShopText("Shop name", shop.name!!)
            ShopText("Description", shop.description!!)
            ShopText("Address", shop.location!!)

            Spacer(modifier = Modifier.height(10.dp))

            Text(
                "Menu",
                color = CanvasBrown,
                fontSize = 25.sp,
                fontWeight = FontWeight.Bold
            )
            shop.menu.forEach { item ->
                Card(
                    modifier = Modifier
                        .fillMaxWidth(),
                    shape = MaterialTheme.shapes.medium,
                    backgroundColor = BackgroundCardColor
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(14.dp)
                    ) {
                        Text(
                            modifier = Modifier.align(Alignment.BottomStart),
                            text = item.name!!,
                            color = OutlineBrown,
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Bold
                        )
                        Text(
                            modifier = Modifier.align(Alignment.BottomEnd),
                            text = item.price.toString() + "???",
                            color = OutlineBrown,
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(20.dp))

            Row(
                horizontalArrangement = Arrangement.SpaceEvenly,
                modifier = Modifier.fillMaxWidth(),
            ) {
                if (!shop.location.isNullOrEmpty()) {
                    FloatingActionButton(
                        onClick = {
                            navController.navigate("MapPage/${shopId}")
                        },
                        backgroundColor = CanvasBrown,
                        contentColor = Color.White,
                        modifier = Modifier
                            .height(30.dp)
                            .width(120.dp)
                    )
                    {
                        Text("See on map")
                    }
                }
                if (!shop.websiteLink.isNullOrEmpty()) {
                    FloatingActionButton(
                        onClick = {
                            val intent = Intent(Intent.ACTION_VIEW)
                            intent.data = Uri.parse(url)
                            context.startActivity(intent)
                        },
                        backgroundColor = CanvasBrown,
                        contentColor = Color.White,
                        modifier = Modifier
                            .height(30.dp)
                            .width(120.dp)
                    )
                    {
                        Text("Website")
                    }

                }


            }

            if (!shop.location.isNullOrEmpty() || !shop.websiteLink.isNullOrEmpty()) {
                Spacer(modifier = Modifier.height(20.dp))
            }

            if (Firebase.auth.currentUser != null) {
                FloatingActionButton(
                    onClick = {
                        ShopRepository.deleteShop(shopId)
                        Toast.makeText(
                            context,
                            "Shop deleted...",
                            Toast.LENGTH_SHORT
                        ).show()
                        navController.navigateUp()
                    },
                    backgroundColor = CanvasBrown,
                    contentColor = Color.White,
                    modifier = Modifier
                        .height(30.dp)
                        .width(120.dp)
                )
                {
                    Text("Delete shop")
                }
                Spacer(modifier = Modifier.height(20.dp))
            }

            FloatingActionButton(
                onClick = {
                    navController.navigate("ShopForm/${shopId}")
                    Log.d("tag", shopId)
                },
                backgroundColor = CanvasBrown,
                contentColor = Color.White,
                modifier = Modifier
                    .height(30.dp)
                    .width(120.dp)
            )
            {
                Text("Edit shop")
            }

            Spacer(modifier = Modifier.height(20.dp))


        }

    }
}

