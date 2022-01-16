package com.example.icecreamworld.ui.buttons

import android.net.Uri
import androidx.activity.compose.ManagedActivityResultLauncher
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.icecreamworld.ui.theme.CanvasBrown

@ExperimentalFoundationApi
@Composable

fun ChoosePhotoButton(
    launcher: ManagedActivityResultLauncher<String, Uri>
) {

    FloatingActionButton(
        onClick = {
            launcher.launch("image/*")
        },
        backgroundColor = CanvasBrown,
        contentColor = Color.White,
        modifier = Modifier
            .height(30.dp)
            .width(140.dp)
    ) {
        Text("Choose photo")
    }

}


//@ExperimentalFoundationApi
//@Composable
//@Preview
//fun InputTextFieldPreview(){
//    InputTextField(label = "text", name = "name")
//}