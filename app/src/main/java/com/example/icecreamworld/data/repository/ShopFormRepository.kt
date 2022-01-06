package com.example.icecreamworld.data.repository


import android.content.ContentValues
import android.util.Log
import com.example.icecreamworld.data.Handler
import com.example.icecreamworld.data.RefName
import com.example.icecreamworld.model.Shop
import com.example.icecreamworld.model.ShopForm
import com.google.firebase.database.ChildEventListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ktx.getValue

object ShopFormRepository: Repository(Handler(RefName.ShopForms)) {

    fun addShopForm(shopForm: ShopForm, customId: String? = null) {
        if (customId != null) {
            handler.setValue(customId, shopForm)
            return
        }
        handler.addValue(shopForm)
    }
    fun changeShopForm(id: String, changedShop: Shop) {
        getShopForm(id)?.let {
            handler.changeValue(id, changedShop)
        }
    }
    fun deleteShopForm(id: String) {
        getShopForm(id)?.let {
            handler.deleteValue(id)
        }
    }

    fun getShopForm(id: String): ShopForm? {
        data.value.forEach {
            if (it.key!! == id)
                return it.getValue<ShopForm>() as ShopForm
        }
        return null
    }

    private fun shopFormExists(shopForm: ShopForm): Boolean {
        data.value.forEach {
            if (shopForm == it.getValue<ShopForm>() as ShopForm)
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