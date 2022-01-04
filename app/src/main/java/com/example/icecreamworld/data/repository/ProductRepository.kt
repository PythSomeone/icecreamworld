package com.example.icecreamworld.data.repository

import android.content.ContentValues
import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import com.google.firebase.database.ChildEventListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError

object ProductRepository {

    private val _products = mutableStateOf<MutableList<DataSnapshot>>(mutableListOf())
    val products: State<MutableList<DataSnapshot>> = _products

    internal fun addProduct(snapshot: DataSnapshot) {
        if (snapshot.key != null) {
            _products.component1().add(snapshot)
        }
    }
    internal fun changeProduct(snapshot: DataSnapshot) {
        snapshot.key?.let { productIndex(it) }
            .run {
                if (this != null)
                    _products.component1().set(this, snapshot)
                else throw error("There is no product with this key, ${snapshot.key}")
            }
    }
    internal fun removeProduct(snapshot: DataSnapshot) {
        snapshot.key?.let { productIndex(it) }
            .run {
                if (this != null)
                    _products.component1().removeAt(this)
                else throw error("There is no product with this key, ${snapshot.key}")
            }
    }

    private fun productIndex(key: String): Int? {
        _products.component1().forEachIndexed { index, snapshot ->
            if (snapshot.key.equals(key))
                return index
        }
        return null
    }

}

object ProductListener: ChildEventListener {
    override fun onChildAdded(snapshot: DataSnapshot, previousChildName: String?) {
        ProductRepository.addProduct(snapshot)
        Log.d(ContentValues.TAG, "$snapshot was added to local repository")
    }
    override fun onChildChanged(snapshot: DataSnapshot, previousChildName: String?) {
        ProductRepository.changeProduct(snapshot)
        Log.d(ContentValues.TAG, "$snapshot was changed in local repository")
    }
    override fun onChildRemoved(snapshot: DataSnapshot) {
        ProductRepository.removeProduct(snapshot)
        Log.d(ContentValues.TAG, "$snapshot was removed from local repository")
    }
    override fun onChildMoved(snapshot: DataSnapshot, previousChildName: String?) {}
    override fun onCancelled(error: DatabaseError) {}
}