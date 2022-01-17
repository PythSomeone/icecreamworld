package com.example.icecreamworld.ui.components

import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.Arrangement.Absolute.Center
import androidx.compose.foundation.layout.Arrangement.Absolute.SpaceBetween
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import coil.compose.rememberImagePainter
import com.example.icecreamworld.data.repository.ShopRepository
import com.example.icecreamworld.model.Product
import com.example.icecreamworld.model.Shop
import com.example.icecreamworld.model.Tag
import com.example.icecreamworld.ui.outlinedtextfields.InputTextField
import com.example.icecreamworld.ui.outlinedtextfields.ProductNameTextField
import com.example.icecreamworld.ui.outlinedtextfields.ProductPriceTextField
import com.example.icecreamworld.ui.theme.BackgroundColor
import com.example.icecreamworld.ui.theme.ButtonBrown
import com.example.icecreamworld.viewmodel.ShopViewModel
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.SwipeRefreshIndicator
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.launch


@ExperimentalMaterialApi
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
    var productName by remember { mutableStateOf("") }
    var price by remember { mutableStateOf("") }
    var tag1 by remember { mutableStateOf("") }
    var tag2 by remember { mutableStateOf("") }
    var tag3 by remember { mutableStateOf("") }
    val addAlertDialog = remember { mutableStateOf(false) }
    val editAlertDialog = remember { mutableStateOf(false) }
    val enabled = remember { mutableStateOf(false) }

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
            .verticalScroll(rememberScrollState())
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth(),
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
                    onClick = {},
                    shape = RoundedCornerShape(6.dp),
                    modifier = Modifier
                        .width(200.dp),
                    content = {
                        Text(
                            text = "Menu",
                            color = Color.White
                        )
                    },
                    colors = ButtonDefaults.buttonColors(backgroundColor = ButtonBrown)
                )

                if (addAlertDialog.value) {
                    AlertDialog(onDismissRequest = { addAlertDialog.value = false },
                        title = { Text(text = "Now you can add product") },
                        text = {
                            Column {
                                OutlinedTextField(
                                    value = productName,
                                    onValueChange = { productName = it },
                                    placeholder = { Text("Name of the product") }
                                )
                                OutlinedTextField(
                                    value = price,
                                    onValueChange = { price = getValidatedNumber(it) },
                                    placeholder = { Text("Price") },
                                    keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number)
                                )
                                OutlinedTextField(
                                    value = tag1,
                                    onValueChange = { tag1 = it },
                                    placeholder = { Text("Tag 1") },
                                )
                                OutlinedTextField(
                                    value = tag2,
                                    onValueChange = { tag2 = it },
                                    placeholder = { Text("Tag 2") },
                                )
                                OutlinedTextField(
                                    value = tag3,
                                    onValueChange = { tag3 = it },
                                    placeholder = { Text("Tag 3") },
                                )
                            }
                        },
                        confirmButton = {
                            enabled.value = productName.isNotEmpty() && price.isNotEmpty()
                            Button(onClick = {
                                menu.value.add(
                                    Product(
                                        productName,
                                        price.toFloat(),
                                        tags = mutableListOf(Tag(tag1), Tag(tag2), Tag(tag3))
                                    )
                                )
                                productName = ""
                                price = ""
                                tag1 = ""
                                tag2 = ""
                                tag3 = ""
                                addAlertDialog.value = false
                            }, enabled = enabled.value) {
                                Text("Confirm")
                            }
                        },
                        dismissButton = {
                            Button(onClick = {
                                productName = ""
                                price = ""
                                tag1 = ""
                                tag2 = ""
                                tag3 = ""
                                addAlertDialog.value = false
                            }) {
                                Text("Back")
                            }
                        })
                }
                val refreshing = remember { mutableStateOf(false) }
                val scope = rememberCoroutineScope()
                SwipeRefresh(
                    state = rememberSwipeRefreshState(refreshing.value),
                    onRefresh = {
                        scope.launch {
                            refreshing.value = true
                            refreshing.value = false
                        }
                    },
                    indicator = { state, refreshTriggerDistance ->
                        SwipeRefreshIndicator(
                            state = state,
                            refreshTriggerDistance = refreshTriggerDistance,
                            scale = true
                        )
                    }
                ) {}
                Column(
                    modifier = modifier
                        .fillMaxWidth()
                ) {
                    menu.value.forEach { item ->
                        if (editAlertDialog.value) {
                            productName = item.name!!
                            price = item.price.toString()
                            tag1 = item.tags[0].name!!
                            tag2 = item.tags[1].name!!
                            tag3 = item.tags[2].name!!
                            AlertDialog(onDismissRequest = { editAlertDialog.value = false },
                                title = { Text(text = "Now you can edit product") },
                                text = {
                                    Column {
                                        OutlinedTextField(
                                            value = productName,
                                            onValueChange = { productName = it },
                                            placeholder = { Text("Name of the product") }
                                        )
                                        OutlinedTextField(
                                            value = price,
                                            onValueChange = { price = getValidatedNumber(it) },
                                            placeholder = { Text("Price") },
                                            keyboardOptions = KeyboardOptions.Default.copy(
                                                keyboardType = KeyboardType.Number
                                            )
                                        )
                                        OutlinedTextField(
                                            value = tag1,
                                            onValueChange = { tag1 = it },
                                            placeholder = { Text("Tag 1") },
                                        )
                                        OutlinedTextField(
                                            value = tag2,
                                            onValueChange = { tag2 = it },
                                            placeholder = { Text("Tag 2") },
                                        )
                                        OutlinedTextField(
                                            value = tag3,
                                            onValueChange = { tag3 = it },
                                            placeholder = { Text("Tag 3") },
                                        )
                                    }
                                },
                                confirmButton = {
                                    enabled.value = productName.isNotEmpty() && price.isNotEmpty()
                                    Button(onClick = {
                                        menu.value.remove(item)
                                        menu.value.add(
                                            Product(
                                                productName,
                                                price.toFloat(),
                                                tags = mutableListOf(
                                                    Tag(tag1),
                                                    Tag(tag2),
                                                    Tag(tag3)
                                                )
                                            )
                                        )
                                        productName = ""
                                        price = ""
                                        tag1 = ""
                                        tag2 = ""
                                        tag3 = ""
                                        editAlertDialog.value = false
                                    }, enabled = enabled.value) {
                                        Text("Confirm")
                                    }
                                },
                                dismissButton = {
                                    Button(onClick = {
                                        productName = ""
                                        price = ""
                                        tag1 = ""
                                        tag2 = ""
                                        tag3 = ""
                                        editAlertDialog.value = false
                                    }) {
                                        Text("Back")
                                    }
                                })
                        }
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = SpaceBetween
                        ) {
                            ListItem(
                                text = {
                                    Row {
                                        Column(Modifier.weight(2f)) {
                                            ProductNameTextField(
                                                label = "Change name",
                                                product = item
                                            )
                                        }
                                        Column(Modifier.weight(1.5f)) {
                                            ProductPriceTextField(
                                                label = "Change price",
                                                product = item
                                            )
                                        }
                                    }
                                },
                                trailing = {
                                    Row {
                                        Icon(
                                            Icons.Default.Edit, "Edit",
                                            Modifier
                                                .clickable { editAlertDialog.value = true }
                                                .padding(top = 30.dp)
                                        )
                                        Icon(
                                            Icons.Default.Delete, "Delete",
                                            modifier = Modifier
                                                .clickable {
                                                    menu.value.remove(item)
                                                    scope.launch {
                                                        refreshing.value = true
                                                        refreshing.value = false
                                                    }
                                                }
                                                .padding(top = 30.dp)
                                        )
                                    }
                                },
                                secondaryText = {
                                    Row(horizontalArrangement = Center) {
                                        Text("Tags: ")
                                        item.tags.forEach { tag ->
                                            tag.name?.let { Text("$it ") }
                                        }
                                    }
                                })
                        }
                    }
                }
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Button(
                        onClick = {
                            addAlertDialog.value = true
                        },
                        shape = RoundedCornerShape(6.dp),
                        modifier = Modifier.padding(top = 10.dp),
                        content = {
                            Text(
                                text = "+",
                                color = Color.White
                            )
                        },
                        colors = ButtonDefaults.buttonColors(backgroundColor = ButtonBrown)
                    )

                    Spacer(Modifier.height(30.dp))

                    Button(
                        enabled = name.value?.isNotEmpty()!!,
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

                            if (Firebase.auth.currentUser != null) {
                                viewModel.editShop(
                                    shop = shopToSubmit,
                                    toChange = shopId,
                                    uri = imageUri
                                )
                            } else {
                                viewModel.sendForm(
                                    shop = shopToSubmit,
                                    toChange = shopId,
                                    uri = imageUri
                                )
                            }
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
            })
    }
}

fun getValidatedNumber(text: String): String {
    val filteredChars = text.filterIndexed { index, c ->
        c in "0123456789" || (c == '.' && text.indexOf('.') == index)
    }
    return if (filteredChars.contains('.')) {
        val beforeDecimal = filteredChars.substringBefore('.')
        val afterDecimal = filteredChars.substringAfter('.')
        beforeDecimal.take(3) + "." + afterDecimal.take(2)
    } else {
        filteredChars.take(3)
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