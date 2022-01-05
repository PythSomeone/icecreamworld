package com.example.icecreamworld

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Card
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import coil.compose.rememberImagePainter
import com.example.icecreamworld.model.Product
import com.example.icecreamworld.model.Shop
import com.example.icecreamworld.ui.appbar.TopAppBar
import com.example.icecreamworld.ui.components.SearchSection
import com.example.icecreamworld.ui.components.ShopsCard
import com.example.icecreamworld.ui.theme.BackgroundCardColor
import com.example.icecreamworld.ui.theme.BackgroundColor
import com.example.icecreamworld.ui.theme.CanvasBrown
import com.example.icecreamworld.ui.theme.OutlineBrown


@Composable
fun ShopScreen(
    navController: NavHostController,
    shopId: String?
) {
    var value = remember { mutableStateOf(TextFieldValue("")) }
    val view = LocalView.current
//    val text = shop.name
    val text = "Shop name"
    val product1 = Product(name = "IceCream", price = 1.0f)
    val product2 = Product(name = "Sweet", price = 2.0f)
    val menu = ArrayList<Product>()
    menu.add(product1)
    menu.add(product2)
    val shop = Shop(
        "abc",
        "def",
        image = "https://firebasestorage.googleapis.com/v0/b/noinstagram-e6c32.appspot.com/o/DEFAULT.png?alt=media&token=5909137c-8e0c-48d1-aa4d-6061cb0b6132",
        menu = menu,
        location = "https://www.google.com/url?sa=i&url=https%3A%2F%2Funsplash.com%2Fimages%2Ffood%2Fice-cream&psig=AOvVaw0kfgGV00R3I20tf7BTZjKX&ust=1641045126027000&source=images&cd=vfe&ved=0CAsQjRxqFwoTCMDV_M-XjvUCFQAAAAAdAAAAABAD"
    )


    Box(
        Modifier
            .fillMaxSize()
            .background(BackgroundColor)
    ) {
        Column(Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {
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
                text,
                color = CanvasBrown,
                fontSize = 30.sp,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(20.dp))

            Card(
                modifier = Modifier
                    .padding(10.dp)
                    .fillMaxWidth()
                    .wrapContentHeight(),
                shape = MaterialTheme.shapes.medium,
                elevation = 15.dp,
                backgroundColor = MaterialTheme.colors.surface
            ) {

                Text(
                    modifier = Modifier
                        .padding(10.dp),
                    text = "description",
                    style = MaterialTheme.typography.h6,
                )

            }

            Spacer(modifier = Modifier.height(10.dp))

            Text(
                "Menu",
                color = CanvasBrown,
                fontSize = 25.sp,
                fontWeight = FontWeight.Bold
            )
            Card(
                modifier = Modifier
                    .padding(10.dp)
                    .fillMaxWidth()
                    .wrapContentHeight(),
                shape = MaterialTheme.shapes.medium,
                backgroundColor = BackgroundCardColor
            ) {
                LazyColumn(
                    modifier = Modifier.fillMaxWidth(),
                ) {
                    items(shop.menu) { shop ->
                        Box(modifier = Modifier
                            .fillMaxWidth()
                            .padding(14.dp)
                        ) {
                            Text(
                                modifier = Modifier.align(Alignment.BottomStart),
                                text = shop.name!!,
                                color = OutlineBrown,
                                fontSize = 14.sp,
                                fontWeight = FontWeight.Bold
                            )
                            Text(
                                modifier = Modifier.align(Alignment.BottomEnd),
                                text = shop.price.toString()!! + "€",
                                color = OutlineBrown,
                                fontSize = 14.sp,
                                fontWeight = FontWeight.Bold
                            )
                        }
                        //                    ProductCard(product.name!!, product.price!!)

                    }
                }
            }
            Row(
                horizontalArrangement = Arrangement.SpaceEvenly,
                modifier = Modifier.fillMaxWidth(),
            ) {

                FloatingActionButton(onClick = { navController.navigate("MapPage") }, backgroundColor = CanvasBrown, contentColor = Color.White, modifier = Modifier
                    .height(30.dp)
                    .width(120.dp)) {
                    Text("See on map")
                }
                FloatingActionButton(onClick = { /*TODO*/ }, backgroundColor = CanvasBrown, contentColor = Color.White, modifier = Modifier
                    .height(30.dp)
                    .width(120.dp)) {
                    Text("Our website")
                }

            }
        }

    }
}

