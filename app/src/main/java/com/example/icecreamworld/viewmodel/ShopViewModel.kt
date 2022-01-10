package com.example.icecreamworld.viewmodel


//import com.example.icecreamworld.utils.LoadingState
//import com.example.icecreamworld.utils.database.ShopHandler
import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class ShopViewModel : ViewModel() {
//    private val loadingState = MutableStateFlow(LoadingState.IDLE)
//    private val postHandler = ShopHandler

    fun uploadImage(uri: Uri, description: String?) = viewModelScope.launch {
//        try {
//            loadingState.emit(LoadingState.LOADING)
//
//
//            postHandler.createNewPost(uri, description)
//
//
//            loadingState.emit(LoadingState.LOADED)
//        } catch (e: Exception) {
//            loadingState.emit(LoadingState.error(e.localizedMessage))
//        }
    }


}