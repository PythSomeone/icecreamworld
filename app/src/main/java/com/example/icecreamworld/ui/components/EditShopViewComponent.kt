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
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberImagePainter
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.icecreamworld.model.Shop
import com.example.icecreamworld.ui.buttons.ChoosePhotoButton
import com.example.icecreamworld.ui.outlinedtextfields.InputTextField
import com.example.icecreamworld.ui.theme.ButtonBrown
import com.example.icecreamworld.viewmodel.ShopViewModel


@ExperimentalFoundationApi
@Composable
fun EditShopSection(
    shop: Shop,
    navController: NavController,
    modifier: Modifier = Modifier,
    viewModel: ShopViewModel = viewModel(),

    ) {
    var description = remember { mutableStateOf(shop.description) }
    var name = remember { mutableStateOf(shop.name) }
    var website = remember { mutableStateOf(shop.websiteLink) }
    var imageUri by remember {
        mutableStateOf<Uri?>(null)
    }
    val bitmap =  remember {
        mutableStateOf<Bitmap?>(null)
    }
    val launcher = rememberLauncherForActivityResult(contract =
    ActivityResultContracts.GetContent()) { uri: Uri? ->
        imageUri = uri
    }
    val context = LocalContext.current
    Column(modifier = modifier.fillMaxWidth()) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp)
        ) {

            Spacer(modifier = Modifier.width(16.dp))
        }

        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,
            content = {
                EditProfileTextBox()

                Spacer(Modifier.height(20.dp))
                if((imageUri != null)) {
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
                                            .aspectRatio(1f, matchHeightConstraintsFirst = true)
                                            .padding(3.dp)
                                            .height(100.dp)
                                    )
                                }
                            }
                        }
                    )
                }
                else{
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
                                    .padding(3.dp)
                                    .height(100.dp)
                            )
                        }
                    )


                }

                Spacer(Modifier.height(20.dp))
                ChoosePhotoButton(launcher)

                Spacer(Modifier.height(20.dp))

                InputTextField(label = "Name", name)
                InputTextField(label = "Description", description)
                InputTextField(label = "Website", website)

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
                            text = "Change location",
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
                        Log.d(TAG, description.value.toString())
                        Log.d(TAG, name.value.toString())
                        Log.d(TAG, imageUri.toString())
//                        val uriExists = imageUri!=null
//                        if(uriExists) {
//                            viewModel.changeData(user, description!!, displayName!!, imageUri!!)
//                        }
//                        else
//                            viewModel.changeData(user, description!!, displayName!!)
//                        navController.navigate(NavigationItem.Profile.route)
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


@Composable
fun EditProfileTextBox() {
    Text(
        "Edit shop",
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







