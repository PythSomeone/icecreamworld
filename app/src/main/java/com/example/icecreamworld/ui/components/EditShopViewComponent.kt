package com.example.icecreamworld.ui.components

import android.content.ContentValues.TAG
import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import android.util.Log
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import coil.compose.rememberImagePainter
import com.example.icecreamworld.data.repository.ShopRepository
import com.example.icecreamworld.model.Product
import com.example.icecreamworld.model.Shop
import com.example.icecreamworld.ui.outlinedtextfields.InputTextField
import com.example.icecreamworld.ui.outlinedtextfields.ProductNameTextField
import com.example.icecreamworld.ui.outlinedtextfields.ProductPriceTextField
import com.example.icecreamworld.ui.theme.BackgroundColor
import com.example.icecreamworld.ui.theme.ButtonBrown
import com.example.icecreamworld.viewmodel.ShopViewModel


@ExperimentalFoundationApi
@Composable
fun ShopFormSection(
    modifier: Modifier = Modifier,
    shopId: String? = null,
    navController: NavController,
    viewModel: ShopViewModel = viewModel(),

    ) {
    var shop = Shop()
    if (shopId != null) {
        shop = ShopRepository.getShop(shopId)!!
    }

    val description = remember { mutableStateOf(shop.description) }
    val name = remember { mutableStateOf(shop.name) }
    val location = remember { mutableStateOf(shop.location) }
    val websiteLink = remember { mutableStateOf(shop.websiteLink) }
    val menu = remember { mutableStateOf(shop.menu) }


    var imageUri by remember {
        mutableStateOf<Uri?>(null)
    }
    val bitmap = remember {
        mutableStateOf<Bitmap?>(null)
    }
    val launcher = rememberLauncherForActivityResult(
        contract =
        ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        imageUri = uri
    }
    val context = LocalContext.current

    Box(
        Modifier
            .fillMaxSize()
            .background(BackgroundColor)
    ) {
        LazyColumn(
            modifier = modifier
                .fillMaxWidth()
        ) {
            item {
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    content = {
                        if (shopId != null) {
                            EditShopTextBox()
                        } else {
                            AddShopTextBox()
                        }

                        Spacer(Modifier.height(20.dp))

                        if ((imageUri != null)) {
                            Column(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(150.dp),
                                horizontalAlignment = Alignment.CenterHorizontally,
                                content = {
                                    imageUri?.let {
                                        if (Build.VERSION.SDK_INT < 28) {
                                            bitmap.value = MediaStore.Images
                                                .Media.getBitmap(context.contentResolver, it)

                                        } else {
                                            val source = ImageDecoder
                                                .createSource(context.contentResolver, it)
                                            bitmap.value = ImageDecoder.decodeBitmap(source)
                                        }
                                        bitmap.value?.let { btm ->
                                            ShopImage(
                                                btm,
                                                modifier = modifier
                                                    .aspectRatio(
                                                        1f,
                                                        matchHeightConstraintsFirst = true
                                                    )
                                                    .height(100.dp)
                                            )
                                        }
                                    }
                                }
                            )
                        } else {
                            Column(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(150.dp),
                                horizontalAlignment = Alignment.CenterHorizontally,
                                content = {
                                    Image(
                                        painter = rememberImagePainter(shop.image),
                                        contentDescription = null,
                                        modifier = modifier
                                            .aspectRatio(1f, matchHeightConstraintsFirst = true)
                                            .height(100.dp)
                                    )
                                }
                            )


                        }


                        Spacer(Modifier.height(20.dp))

                        Button(
                            onClick = {
                                launcher.launch("image/*")
                            },
                            shape = RoundedCornerShape(6.dp),
                            modifier = Modifier
                                .width(200.dp),
                            content = {
                                Text(
                                    text = "Choose photo",
                                    color = Color.White
                                )
                            },
                            colors = ButtonDefaults.buttonColors(backgroundColor = ButtonBrown)
                        )

                        Spacer(Modifier.height(20.dp))

                        InputTextField(label = "Change name", name = name)
                        InputTextField(label = "Change description", name = description)
                        InputTextField(label = "Change location", name = location)
                        InputTextField(label = "Change website address", name = websiteLink)

                        Spacer(Modifier.height(20.dp))

                        Button(
                            onClick = {
                                /*TODO*/
                            },
                            shape = RoundedCornerShape(6.dp),
                            modifier = Modifier
                                .width(200.dp),
                            content = {
                                Text(
                                    text = "Change menu",
                                    color = Color.White
                                )
                            },
                            colors = ButtonDefaults.buttonColors(backgroundColor = ButtonBrown)
                        )

                        menu.value.forEach { item ->
                            Row(
                                modifier = Modifier.fillMaxSize(),
                                horizontalArrangement = Arrangement.SpaceBetween

                            ) {
                                ProductNameTextField(label = "Change name", product = item)
                                ProductPriceTextField(label = "Change price", product = item)
                                Button(
                                    onClick = {

                                    },
                                    shape = RoundedCornerShape(6.dp),
                                    modifier = Modifier,
                                    content = {
                                        Text(
                                            text = "x",
                                            color = Color.White
                                        )
                                    },
                                    colors = ButtonDefaults.buttonColors(backgroundColor = ButtonBrown)
                                )
                            }
                        }
                        Button(
                            onClick = {
                                shop.menu.add(Product())
                                Log.d(TAG, "hello" + shop.toString())
                            },
                            shape = RoundedCornerShape(6.dp),
                            modifier = Modifier,
                            content = {
                                Text(
                                    text = "+",
                                    color = Color.White
                                )
                            },
                            colors = ButtonDefaults.buttonColors(backgroundColor = ButtonBrown)
                        )

                        Spacer(Modifier.height(20.dp))

                        Button(
                            onClick = {
                                Toast.makeText(
                                    context,
                                    "Changes submitted...",
                                    Toast.LENGTH_SHORT
                                ).show()

                                val shopToSubmit = Shop(
                                    name = name.value,
                                    description = description.value,
                                    location = location.value,
                                    websiteLink = websiteLink.value,
                                    menu = menu.value
                                )

                                Log.i(TAG,"ShopToSubmit = $shopToSubmit")
                                viewModel.sendForm(
                                    shop = shopToSubmit,
                                    toChange = shopId,
                                    uri = imageUri
                                )

                                if (shopId != null) {
                                    navController.navigate("Shop/${shopId}")
                                } else {
                                    navController.navigate("HomePage")
                                }

                            },
                            shape = RoundedCornerShape(6.dp),
                            modifier = Modifier
                                .width(200.dp),
                            content = {
                                Text(
                                    text = "Save",
                                    color = Color.White,
                                )
                            },
                            colors = ButtonDefaults.buttonColors(backgroundColor = ButtonBrown)
                        )


                    }
                )
            }


        }
    }
}


@Composable
fun EditShopTextBox() {
    Text(
        "Edit shop form",
        Modifier.padding(end = 10.dp),
        fontSize = 25.sp

    )
}

@Composable
fun AddShopTextBox() {
    Text(
        "Add shop form",
        Modifier.padding(end = 10.dp),
        fontSize = 25.sp

    )
}

@Composable
fun ShopImage(
    bitmap: Bitmap,
    modifier: Modifier = Modifier
) {
    Image(
        bitmap = bitmap.asImageBitmap(),
        contentDescription = null,
        modifier = modifier
            .aspectRatio(1f, matchHeightConstraintsFirst = true)
    )
}







