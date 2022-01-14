package com.example.icecreamworld

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.icecreamworld.ui.theme.BackgroundColor
import com.example.icecreamworld.ui.theme.CanvasBrown
import com.example.icecreamworld.viewmodel.LoginViewModel
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun LoginScreen(navController: NavHostController, viewModel: LoginViewModel = viewModel()) {
    var userEmail by remember { mutableStateOf("") }
    var userPassword by remember { mutableStateOf("") }
    var passwordVisibility by remember { mutableStateOf(false) }
    val icon = if (passwordVisibility)
        painterResource(id = R.drawable.ic_visibility_on)
    else
        painterResource(id = R.drawable.ic_visibility_off)
    val context = LocalContext.current
    val scope = rememberCoroutineScope()

    HomeScreenBackground()

    Column(
        Modifier
            .fillMaxSize()
            .background(color = BackgroundColor), Arrangement.Center, Alignment.CenterHorizontally
    ) {
        OutlinedTextField(
            modifier = Modifier.width(300.dp),
            value = userEmail,
            colors = TextFieldDefaults.outlinedTextFieldColors(
                focusedBorderColor = CanvasBrown,
                unfocusedBorderColor = CanvasBrown,
                textColor = Color.White
            ),
            shape = RoundedCornerShape(24.dp),
            label = {
                Text(
                    text = "Email",
                    color = CanvasBrown,
                    modifier = Modifier.padding(start = 20.dp)
                )
            },
            onValueChange = {
                userEmail = it
            })

        Spacer(Modifier.height(20.dp))

        OutlinedTextField(
            value = userPassword,
            onValueChange = {
                userPassword = it
            },
            modifier = Modifier.width(300.dp),
            colors = TextFieldDefaults.outlinedTextFieldColors(
                focusedBorderColor = CanvasBrown,
                unfocusedBorderColor = CanvasBrown,
                textColor = Color.White
            ),
            shape = RoundedCornerShape(24.dp),
            label = {
                Text(
                    text = "Password",
                    color = CanvasBrown,
                    modifier = Modifier.padding(start = 20.dp)
                )
            },
            trailingIcon = {
                IconButton(onClick = {
                    passwordVisibility = !passwordVisibility
                }) {
                    Icon(
                        painter = icon,
                        contentDescription = "Visibility icon"
                    )
                }
            },
            visualTransformation = if (passwordVisibility) VisualTransformation.None
            else PasswordVisualTransformation()
        )

        Spacer(Modifier.height(40.dp))

        Button(
            enabled = userEmail.isNotEmpty() && userPassword.isNotEmpty(),
            onClick = {
                scope.launch {
                    viewModel.signInWithEmailAndPassword(
                        userEmail.trim(),
                        userPassword.trim(),
                        navController
                    )
                    delay(2000)
                    if (Firebase.auth.currentUser != null) {
                        Toast.makeText(
                            context,
                            "Successfully logged in...",
                            Toast.LENGTH_SHORT
                        ).show()
                    } else {
                        Toast.makeText(
                            context,
                            "Wrong email or password!",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }

            },
            shape = RoundedCornerShape(24.dp),
            modifier = Modifier
                .width(300.dp)
                .alpha(0.7f),
            content = {
                Text(text = "Login")
            },
            colors = ButtonDefaults.buttonColors(backgroundColor = CanvasBrown)
        )
    }

}