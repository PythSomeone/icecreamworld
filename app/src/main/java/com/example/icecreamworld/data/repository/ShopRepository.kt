package com.example.icecreamworld.data.repository

import android.content.ContentValues
import android.net.Uri
import android.util.Log
import com.example.icecreamworld.data.Folder
import com.example.icecreamworld.data.Handler
import com.example.icecreamworld.data.handler.StorageHandler
import com.example.icecreamworld.model.Shop
import com.example.icecreamworld.model.ShopForm
import com.google.firebase.database.ChildEventListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ktx.getValue

object ShopRepository : Repository(Handler(Folder.Shops)) {

    internal fun addShop(shop: Shop, uri: Uri? = null) {
        if (shopExists(shop).not()) {
            handler.addValue(shop)
            TagUses(shop).increase()
        }
    }

    internal fun changeShop(id: String, changedShop: Shop, uri: Uri? = null) {
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
            StorageHandler.removePicture(it.image!!)
        }
        ShopFormRepository.clearFormsFor(id)
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

    private object Listener : ChildEventListener {
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

object ShopFormRepository : Repository(Handler(Folder.ShopForms)) {

    suspend fun addShopForm(shopForm: ShopForm, uri: Uri? = null): String {
        val id: String = handler.addValue(shopForm)
        if (uri != null) {
            setPicture(
                uri = uri,
                id = id,
                shopForm = shopForm
            )
        }
        return id
    }

    fun approveShopForm(id: String) {
        val shopForm = getShopForm(id) ?: return
        if (shopForm.shop == null) return

        if (shopForm.toChange != null) {
            val shopToChange = ShopRepository.getShop(shopForm.toChange)
            if (shopForm.shop.image != shopToChange!!.image)
                StorageHandler.removePicture(shopToChange.image!!)
            ShopRepository.changeShop(shopForm.toChange, shopForm.shop)
        } else
            ShopRepository.addShop(shopForm.shop)

        handler.deleteValue(id)
    }

    fun rejectShopForm(id: String) {
        val shopForm = getShopForm(id) ?: return

        StorageHandler.removePicture(shopForm.shop!!.image!!)
        handler.deleteValue(id)
    }

    fun getShopForm(id: String): ShopForm? {
        data.value.forEach {
            if (it.key!! == id)
                return it.getValue<ShopForm>() as ShopForm
        }
        return null
    }

    internal fun clearFormsFor(id: String) {
        findFormsChanging(id).forEach {
            rejectShopForm(it)
        }
    }

    private fun findFormsChanging(id: String): MutableList<String> {
        val listToDelete = mutableListOf<String>()
        data.value.forEach {
            val form = getShopForm(it.key!!)
            if (form!!.toChange == id)
                listToDelete.add(it.key!!)
        }
        return listToDelete
    }

    private suspend fun setPicture(uri: Uri, id: String, shopForm: ShopForm) {
        handler.changeValue(
            id = id,
            obj = shopForm.copy(
                shop = shopForm.shop!!.copy(
                    image =
                    StorageHandler.uploadPicture(
                        uri = uri,
                        folder = Folder.Shops,
                        dataId = id
                    )
                )
            )
        )
    }

    fun listenToChanges() {
        handler.initializeListener(Listener)
    }

    private object Listener : ChildEventListener {
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


