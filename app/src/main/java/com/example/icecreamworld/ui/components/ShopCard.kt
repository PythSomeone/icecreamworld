package com.example.icecreamworld.ui.components

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.Card
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.icecreamworld.R
import com.example.icecreamworld.model.Shop
import com.example.icecreamworld.ui.theme.CanvasBrown


@Composable
fun ShopsCard(
    navController: NavHostController,
    name: String,
    description: String,
    image: String,
    key: String,
) {
    val shop = Shop(
        "123",
        "abc",
        "def",
        "Wrocław, Legnicka 7"
    )
    Log.d("TAG", key.toString())
    Card(
        modifier = Modifier
            .padding(10.dp)
            .fillMaxWidth()
            .wrapContentHeight(),
        shape = MaterialTheme.shapes.medium,
        elevation = 15.dp,
        backgroundColor = MaterialTheme.colors.surface
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = painterResource(R.drawable.ic_launcher_foreground),
                contentDescription = null,
                modifier = Modifier
                    .size(130.dp)
                    .padding(8.dp),
                contentScale = ContentScale.Fit,
            )
            Column(
                Modifier
                    .padding(8.dp)
                    .fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally) {
                Text(
                    text = name,
                    style = MaterialTheme.typography.h4,
                    color = CanvasBrown,
                )
                Text(
                    text = description,
                    style = MaterialTheme.typography.body2,
                    color = CanvasBrown
                )
                Spacer(modifier = Modifier.height(10.dp))
                FloatingActionButton(onClick = { navController.navigate("ShopScreen/${key}") }, backgroundColor = CanvasBrown, contentColor = Color.White, modifier = Modifier
                    .height(30.dp)
                    .width(150.dp)) {
                    Text("See More")
                }
            }
        }
    }
}