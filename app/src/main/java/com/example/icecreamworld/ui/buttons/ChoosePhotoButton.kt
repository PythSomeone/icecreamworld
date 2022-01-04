package com.example.icecreamworld.ui.buttons

import android.net.Uri
import androidx.activity.compose.ManagedActivityResultLauncher
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.icecreamworld.ui.theme.ButtonBrown

@ExperimentalFoundationApi
@Composable

fun ChoosePhotoButton(
    launcher: ManagedActivityResultLauncher<String, Uri>
) {

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
}


//@ExperimentalFoundationApi
//@Composable
//@Preview
//fun InputTextFieldPreview(){
//    InputTextField(label = "text", name = "name")
//}