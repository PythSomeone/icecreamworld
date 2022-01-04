package com.example.icecreamworld.data.repository

import android.content.ContentValues
import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import com.google.firebase.database.ChildEventListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError

object ShopRepository {

    private val _shops = mutableStateOf<MutableList<DataSnapshot>>(mutableListOf())
    val shops: State<MutableList<DataSnapshot>> = _shops

    internal fun addShop(snapshot: DataSnapshot) {
        if (snapshot.key != null) {
            _shops.component1().add(snapshot)
        }
    }
    internal fun changeShop(snapshot: DataSnapshot) {
        snapshot.key?.let { shopIndex(it) }
            .run {
                if (this != null)
                    _shops.component1().set(this, snapshot)
                else throw error("There is no shop with this key, ${snapshot.key}")
            }
    }
    internal fun removeShop(snapshot: DataSnapshot) {
        snapshot.key?.let { shopIndex(it) }
            .run {
                if (this != null)
                    _shops.component1().removeAt(this)
                else throw error("There is no shop with this key, ${snapshot.key}")
            }
    }

    private fun shopIndex(key: String): Int? {
        _shops.component1().forEachIndexed { index, snapshot ->
            if (snapshot.key.equals(key))
                return index
        }
        return null
    }

}

object ShopListener: ChildEventListener {
    override fun onChildAdded(snapshot: DataSnapshot, previousChildName: String?) {
        ShopRepository.addShop(snapshot)
        Log.d(ContentValues.TAG, "$snapshot was added to local repository")
    }
    override fun onChildChanged(snapshot: DataSnapshot, previousChildName: String?) {
        ShopRepository.changeShop(snapshot)
        Log.d(ContentValues.TAG, "$snapshot was changed in local repository")
    }
    override fun onChildRemoved(snapshot: DataSnapshot) {
        ShopRepository.removeShop(snapshot)
        Log.d(ContentValues.TAG, "$snapshot was removed from local repository")
    }
    override fun onChildMoved(snapshot: DataSnapshot, previousChildName: String?) {}
    override fun onCancelled(error: DatabaseError) {}
}