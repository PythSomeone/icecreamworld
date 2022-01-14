package com.example.icecreamworld.viewmodel


import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.icecreamworld.data.repository.ShopFormRepository
import com.example.icecreamworld.data.repository.ShopRepository
import com.example.icecreamworld.model.Product
import com.example.icecreamworld.model.Shop
import com.example.icecreamworld.model.ShopForm
import kotlinx.coroutines.delay
import com.example.icecreamworld.utils.LoadingState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class ShopViewModel : ViewModel() {
    private val loadingState = MutableStateFlow(LoadingState.IDLE)
    private val _shop = MutableStateFlow(Shop())
    val shop: StateFlow<Shop> = _shop
    private val _menu = MutableStateFlow(emptyList<Product>())
    val menu: StateFlow<List<Product>> = _menu

    fun sendForm(shop: Shop, toChange: String?=null, uri: Uri?=null) = viewModelScope.launch {
        try {
            loadingState.emit(LoadingState.LOADING)

            val shopForm = ShopForm(shop = shop, toChange = toChange)

            ShopFormRepository.addShopForm(shopForm = shopForm, uri = uri)

            loadingState.emit(LoadingState.LOADED)
        } catch (e: Exception) {
            loadingState.emit(LoadingState.error(e.localizedMessage))
        }
    }

    fun editShop(shop: Shop, toChange: String?=null, uri: Uri?=null) = viewModelScope.launch {
        try {
            loadingState.emit(LoadingState.LOADING)

            val id : String
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