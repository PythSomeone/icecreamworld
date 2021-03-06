package com.example.icecreamworld.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ListItem
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import com.example.icecreamworld.data.repository.ShopFormRepository
import com.example.icecreamworld.model.ShopForm
import com.example.icecreamworld.ui.theme.BackgroundColor
import com.example.icecreamworld.ui.theme.CanvasBrown
import com.google.firebase.database.ktx.getValue

@ExperimentalMaterialApi
@Composable
fun ApproveListScreen(navController: NavController) {
    var value = remember { mutableStateOf(TextFieldValue("")) }
    val view = LocalView.current
    val text = "Select the shop to approve"
    val forms = ShopFormRepository
    Box(
        Modifier
            .fillMaxSize()
            .background(BackgroundColor)
    ) {
        Column(Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {
            Text(
                text,
                color = CanvasBrown,
                fontSize = 21.sp,
                fontWeight = FontWeight.Bold
            )
            SearchSection(
                textValue = value,
                label = "",
                onDoneActionClick =
                {
                    view.clearFocus()
                },
                onValueChanged = {},
                onClearClick = {
                    value = value
                    view.clearFocus()
                },
                navController = navController as NavHostController
            )
            Spacer(modifier = Modifier.height(20.dp))
            LazyColumn(
                modifier = Modifier.fillMaxWidth(),
                contentPadding = PaddingValues(16.dp)
            ) {
                items(forms.data.value) { snapshot ->
                    val formId = snapshot.key
                    ListItem(
                        Modifier.clickable { navController.navigate("ManageForm/${formId.toString()}") },
                        text = { Text(snapshot.getValue<ShopForm>()?.shop?.name!!) },
                        trailing = {
                            //Icon(Icons.Default.Delete, "Delete", Modifier.clickable { ShopRepository.deleteShop(snapshot.getValue<Shop>()?.name!!) })
                        }
                    )


                }
            }
        }
    }
}