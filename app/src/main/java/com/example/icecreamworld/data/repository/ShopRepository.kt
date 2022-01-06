package com.example.icecreamworld.data.repository

import android.content.ContentValues
import android.util.Log
import com.example.icecreamworld.data.Handler
import com.example.icecreamworld.data.RefName
import com.example.icecreamworld.model.Shop
import com.google.firebase.database.ChildEventListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ktx.getValue

object ShopRepository: Repository(Handler(RefName.Shops)) {

    fun approveShop(formId: String, customShopId: String? = null) {
        val shopForm = ShopFormRepository.getShopForm(formId) ?: return
        if (shopForm.shop == null)
            return
        if (shopForm.toChange != null) {
            changeShop(shopForm.toChange, shopForm.shop)
        }
        else {
            addShop(shopForm.shop)
        }
        ShopFormRepository.deleteShopForm(formId)
    }
    private fun addShop(shop: Shop, customId: String? = null) {
        if (shopExists(shop).not()) {
            if (customId == null) {
                handler.addValue(shop)
            }
            else
                handler.setValue(customId, shop)
            TagUses(shop).increase()
        }
    }
    private fun changeShop(id: String, changedShop: Shop) {
        getShop(id)?.let {
            if (it == changedShop)
                return
            handler.changeValue(id, changedShop)
            TagUses(changedShop, it).increase()
        }
    }
    fun deleteShop(id: String) {
        getShop(id)?.let {
            TagUses(it).decrease()
            handler.deleteValue(id)
        }
    }

    fun getShop(id: String): Shop? {
        data.value.forEach {
            if (it.key!! == id)
                return it.getValue<Shop>() as Shop
        }
        return null
    }

    private fun shopExists(shop: Shop): Boolean {
        data.value.forEach {
            if (shop == it.getValue<Shop>() as Shop)
                return true
        }
        return false
    }

    fun listenToChanges() {
        handler.initializeListener(Listener)
    }

    private object Listener: ChildEventListener {
        override fun onChildAdded(snapshot: DataSnapshot, previousChildName: String?) {
            addData(snapshot)
            Log.d(ContentValues.TAG, "$snapshot was added to local repository")
        }

        override fun onChildChanged(snapshot: DataSnapshot, previousChildName: String?) {
            changeData(snapshot)
            Log.d(ContentValues.TAG, "$snapshot was changed in local repository")
        }

        override fun onChildRemoved(snapshot: DataSnapshot) {
            removeData(snapshot)
            Log.d(ContentValues.TAG, "$snapshot was removed from local repository")
        }

        override fun onChildMoved(snapshot: DataSnapshot, previousChildName: String?) {}
        override fun onCancelled(error: DatabaseError) {}
    }

}

//class MenuManager(data: DataSnapshot) {
//
//    private val id: String? = data.key
//    private val shop: Shop = ShopRepository.getShop(id!!)!!
//
//    fun addProduct(product: Product) {
//        shop.menu.add(product)
//        ShopRepository.approveShop(id!!, shop)
//    }
//    fun updateProduct(index: Int, updatedProduct: Product) {
//        if (recordExists(index)) {
//            shop.menu[index] = updatedProduct
//            ShopRepository.approveShop(id!!, shop)
//        }
//    }
//    fun removeProduct(index: Int) {
//        if (recordExists(index)) {
//            shop.menu.removeAt(index)
//            ShopRepository.approveShop(id!!, shop)
//        }
//    }
//    fun clear() {
//        shop.menu.clear()
//        ShopRepository.approveShop(id!!, shop)
//    }
//
//    private fun recordExists(index: Int): Boolean {
//        if (shop.menu.isEmpty() || shop.menu.size <= index)
//            return false
//        return true
//    }
//
//}


