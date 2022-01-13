package com.example.icecreamworld.ui.components

import android.widget.Toast
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
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
import com.example.icecreamworld.data.repository.ShopFormRepository
import com.example.icecreamworld.model.Shop
import com.example.icecreamworld.ui.theme.BackgroundCardColor
import com.example.icecreamworld.ui.theme.BackgroundColor
import com.example.icecreamworld.ui.theme.CanvasBrown
import com.example.icecreamworld.ui.theme.OutlineBrown


@ExperimentalFoundationApi
@Composable
fun ManageFormsPage(
    navController: NavHostController,
    shopFormId: String?
) {
    var shopForm = Shop()
    val form = ShopFormRepository.getShopForm(shopFormId!!)
    if(form != null){
        shopForm = form.shop!!
    }


    val scrollState = rememberScrollState()
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
            Row(Modifier.fillMaxWidth()){
                Icon(Icons.Default.ArrowBack,"Back",
                    Modifier
                        .clickable { navController.navigateUp() }
                        .padding(top = 15.dp, start = 0.dp))
            }
            if(form?.toChange == null){
                AddShopTextBox()
            }
            else{
                EditShopTextBox()
            }
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                content = {
                    Image(
                        painter = rememberImagePainter(shopForm.image),
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
                shopForm.name!!,
                color = CanvasBrown,
                fontSize = 30.sp,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(20.dp))

            ShopText("Shop name", shopForm.name!!)
            ShopText("Description", shopForm.description!!)
            ShopText("Address", shopForm.location!!)
            ShopText("Website", shopForm.websiteLink!!)

            Spacer(modifier = Modifier.height(10.dp))

            Text(
                "Menu",
                color = CanvasBrown,
                fontSize = 25.sp,
                fontWeight = FontWeight.Bold
            )
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight(),
                shape = MaterialTheme.shapes.medium,
                backgroundColor = BackgroundCardColor
            ) {

                    shopForm.menu.forEach { item ->
                        Box(modifier = Modifier
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
                                text = item.price.toString() + "€",
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
                FloatingActionButton(
                    onClick = {
                        ShopFormRepository.approveShopForm(shopFormId)
                        Toast.makeText(
                            context,
                            "Form accepted...",
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
                    Text("Accept")
                }
                FloatingActionButton(
                    onClick = {
                        ShopFormRepository.rejectShopForm(shopFormId)
                        Toast.makeText(
                            context,
                            "Form rejected...",
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
                    Text("Reject")
                }



            }

            Spacer(modifier = Modifier.height(20.dp))


        }

    }
}
@Composable
fun AcceptAddTextBox() {
    Text(
        "Add shop form",
        Modifier.padding(end = 10.dp),
        fontSize = 25.sp

    )
}
@Composable
fun AcceptEditTextBox() {
    Text(
        "Edit shop form",
        Modifier.padding(end = 10.dp),
        fontSize = 25.sp

    )
}
