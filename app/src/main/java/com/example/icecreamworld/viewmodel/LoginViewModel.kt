package com.example.icecreamworld.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.example.icecreamworld.ui.components.DrawerScreens
import com.example.icecreamworld.utils.LoadingState
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

class LoginViewModel : ViewModel() {
    val loadingState = MutableStateFlow(LoadingState.IDLE)

    fun signInWithEmailAndPassword(email: String, password: String, navController: NavController) =
        viewModelScope.launch {
            try {
                loadingState.emit(LoadingState.LOADING)
                Firebase.auth.signInWithEmailAndPassword(email, password).await()
                loadingState.emit(LoadingState.LOADED)
                navController.navigate(DrawerScreens.Proposed.route)
            } catch (e: Exception) {
                loadingState.emit(LoadingState.error(e.localizedMessage))
            }
        }
}