package com.example.icecreamworld.ui.components

//import com.example.icecreamworld.utils.NavigationItem
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.icecreamworld.ui.theme.ButtonBrown
import com.example.icecreamworld.ui.theme.OutlineBrown
import com.example.icecreamworld.viewmodel.ShopViewModel


@ExperimentalFoundationApi
@Composable
fun AddShopSection(
    navController: NavController,
    modifier: Modifier = Modifier,
    viewModel: ShopViewModel = viewModel(),
) {
    var shopName by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }
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
                AddPostTextBox()

                Spacer(Modifier.height(20.dp))

                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(200.dp),
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
                                Image(
                                    bitmap = btm.asImageBitmap(),
                                    contentDescription = null,
                                    modifier = Modifier.size(400.dp)
                                )
                            }
                        }
                    }
                )


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

                Spacer(Modifier.height(10.dp))

                OutlinedTextField(
                    value = shopName,
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        focusedBorderColor = OutlineBrown,
                        unfocusedBorderColor = OutlineBrown,
                    ),
                    shape = RoundedCornerShape(15.dp),
                    label =
                    {
                        Text(
                            textAlign = TextAlign.Center,
                            text = "Name",
                            fontSize = 15.sp,
                            color = ButtonBrown,
                            fontWeight = FontWeight.Bold
                        )

                    },
                    onValueChange = {
                        shopName = it
                    },
                    modifier = Modifier
                        .width(300.dp)
                )

                Spacer(Modifier.height(10.dp))

                OutlinedTextField(
                    value = description,
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        focusedBorderColor = OutlineBrown,
                        unfocusedBorderColor = OutlineBrown,
                    ),
                    shape = RoundedCornerShape(15.dp),
                    label =
                    {
                        Text(
                            textAlign = TextAlign.Center,
                            text = "Description",
                            fontSize = 15.sp,
                            color = ButtonBrown,
                            fontWeight = FontWeight.Bold
                        )

                    },
                    onValueChange = {
                        description = it
                    },
                    modifier = Modifier
                        .width(300.dp)
                )

                Spacer(Modifier.height(20.dp))

                Button(
                    onClick = {},//TODO
                    shape = RoundedCornerShape(6.dp),
                    modifier = Modifier
                        .width(200.dp),
                    content = {
                        Text(
                            text = "Choose location",
                            color = Color.White,
                        )
                    },
                    colors = ButtonDefaults.buttonColors(backgroundColor = ButtonBrown)
                )

                Spacer(Modifier.height(20.dp))

                if (imageUri != null) {
                    Button(
                        onClick = {
                            Toast.makeText(
                                context,
                                "Form submitted...",
                                Toast.LENGTH_SHORT
                            ).show()
                            Log.d("TAG", "$imageUri")
                            viewModel.uploadImage(imageUri!!, description)
                            imageUri = null
                            //navController.navigate(NavigationItem.Home.route)

                        },
                        shape = RoundedCornerShape(6.dp),
                        modifier = Modifier
                            .width(200.dp),
                        content = {
                            Text(
                                text = "Submit shop",
                                color = Color.White,
                            )
                        },
                        colors = ButtonDefaults.buttonColors(backgroundColor = ButtonBrown)
                    )

                }


            }
        )

    }
}


@Composable
fun AddPostTextBox() {
    Text(
        "Shop form",
        Modifier.padding(end = 10.dp),
        fontSize = 25.sp


    )
}