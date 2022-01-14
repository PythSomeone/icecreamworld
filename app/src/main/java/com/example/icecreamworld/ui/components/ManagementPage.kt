package com.example.icecreamworld.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.unit.toSize
import androidx.navigation.NavHostController
import com.example.icecreamworld.HomeScreenBackground
import com.example.icecreamworld.ui.canva.IceCreamWorldCanvas
import com.example.icecreamworld.ui.theme.BackgroundCardColor
import com.example.icecreamworld.ui.theme.ButtonBrown
import com.example.icecreamworld.ui.theme.CanvasBrown

@Composable
fun ManagementScreen(navController: NavHostController) {
    HomeScreenBackground()
    var expanded by remember { mutableStateOf(false) }
    val suggestions = listOf("Manage ICS forms", "Manage ICS")
    var selectedText by remember { mutableStateOf("Choose what you want to do") }
    var textFieldSize by remember { mutableStateOf(Size.Zero) }

    Row(Modifier.fillMaxWidth()){
        Icon(Icons.Default.ArrowBack,"Back", Modifier.clickable { navController.navigateUp() }.padding(top = 15.dp, start = 15.dp))
    }
    Column(
        horizontalAlignment = CenterHorizontally,
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.Top
    ) {
        Row(Modifier.fillMaxWidth(), verticalAlignment = Alignment.Top) {
            IceCreamWorldCanvas()
        }
        Spacer(modifier = Modifier.height(80.dp))
        Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
            Text(
                "Management Panel",
                color = CanvasBrown,
                fontWeight = FontWeight.Bold,
                fontSize = 25.sp,
                textAlign = TextAlign.Center
            )
        }
        Spacer(modifier = Modifier.height(50.dp))
        Column(verticalArrangement = Arrangement.Center,horizontalAlignment = CenterHorizontally, modifier = Modifier.fillMaxWidth().padding(horizontal = 80.dp)) {
            Button(
                onClick = {expanded = true},
                modifier = Modifier
                    .fillMaxWidth()
                    .onGloballyPositioned { coordinates ->
                        textFieldSize = coordinates.size.toSize()
                    },
                content = {Text(text = selectedText)},
                colors = ButtonDefaults.buttonColors(backgroundColor = ButtonBrown)
            )
            DropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false },
                modifier = Modifier
                    .width(with(LocalDensity.current) { textFieldSize.width.toDp() })
            ) {
                suggestions.forEach { label ->
                    DropdownMenuItem(onClick = {
                        selectedText = label
                        if(selectedText == "Manage ICS forms"){
                            navController.navigate("ApproveListScreen")
                        }
                        if(selectedText == "Manage ICS"){
                            navController.navigate("EditListScreen")
                        }
                    },
                    modifier = Modifier.background(color = BackgroundCardColor)) {
                        Column(horizontalAlignment = CenterHorizontally) {
                            Text(text = label)
                            Divider()
                        }
                    }
                }
            }
        }
    }
}
