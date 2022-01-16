package com.example.icecreamworld.viewmodel


import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.icecreamworld.data.repository.ShopFormRepository
import com.example.icecreamworld.model.Shop
import com.example.icecreamworld.model.ShopForm
import com.example.icecreamworld.utils.LoadingState
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class ShopViewModel : ViewModel() {
    private val loadingState = MutableStateFlow(LoadingState.IDLE)

    fun sendForm(shop: Shop, toChange: String? = null, uri: Uri? = null) = viewModelScope.launch {
        try {
            loadingState.emit(LoadingState.LOADING)

            val shopForm = ShopForm(shop = shop, toChange = toChange)

            ShopFormRepository.addShopForm(shopForm = shopForm, uri = uri)

            loadingState.emit(LoadingState.LOADED)
        } catch (e: Exception) {
            loadingState.emit(LoadingState.error(e.localizedMessage))
        }
    }

    fun editShop(shop: Shop, toChange: String? = null, uri: Uri? = null) = viewModelScope.launch {
        try {
            loadingState.emit(LoadingState.LOADING)

            val id: String
            val shopForm = ShopForm(shop = shop, toChange = toChange)

            id = ShopFormRepository.addShopForm(shopForm = shopForm, uri = uri)

            delay(1000)

            ShopFormRepository.approveShopForm(id)


            loadingState.emit(LoadingState.LOADED)
        } catch (e: Exception) {
            loadingState.emit(LoadingState.error(e.localizedMessage))
        }
    }
}